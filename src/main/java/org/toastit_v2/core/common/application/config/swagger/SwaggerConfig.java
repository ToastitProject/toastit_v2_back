package org.toastit_v2.core.common.application.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.code.CommonResponseCode;
import org.toastit_v2.core.common.web.response.SuccessResponse;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_AUTH_SCHEME = "Bearer Authentication";

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_SCHEME))
                .components(new Components().addSecuritySchemes(BEARER_AUTH_SCHEME, createJwtBearerScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("Toastit V2 API")
                .description("미정")
                .version("2.0");
    }

    private SecurityScheme createJwtBearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("Bearer");
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            ResponseCodeAnnotation annotation = handlerMethod.getMethodAnnotation(ResponseCodeAnnotation.class);
            CommonResponseCode responseCode = Optional.ofNullable(annotation)
                    .map(ResponseCodeAnnotation::value)
                    .orElse(CommonResponseCode.SUCCESS);

            applyResponseSchemaWrapper(operation, SuccessResponse.class, "data", responseCode);

            Optional.ofNullable(handlerMethod.getMethodAnnotation(ExceptionCodeAnnotations.class))
                    .map(ExceptionCodeAnnotations::value)
                    .ifPresent(exceptionCodes -> {
                        for (CommonExceptionCode exceptionCode : exceptionCodes) {
                            addExceptionSchema(operation, exceptionCode);
                        }
                    });

            return operation;
        };
    }

    private void applyResponseSchemaWrapper(Operation operation,
                                            Class<?> type,
                                            String wrapFieldName,
                                            CommonResponseCode responseCode
    ) {
        ApiResponses responses = operation.getResponses();
        String responseCodeKey = String.valueOf(responseCode.getHttpStatus().value());

        if (isNonSuccessCode(responseCodeKey)) {
            manageNon200Response(responses, responseCodeKey);
        }

        ApiResponse response = responses.computeIfAbsent(responseCodeKey, key -> new ApiResponse());
        response.setDescription(responseCode.getData());
        Content content = response.getContent();

        Optional.ofNullable(content).ifPresent(c -> c.values().stream()
                .filter(Objects::nonNull)
                .map(MediaType::getSchema)
                .forEach(schema -> createWrappedSchema(schema, type, wrapFieldName, responseCode)));
    }

    private boolean isNonSuccessCode(String responseCodeKey) {
        return !"200".equals(responseCodeKey);
    }

    private void manageNon200Response(ApiResponses responses, String responseCodeKey) {
        ApiResponse existingResponse = responses.get("200");

        if (existingResponse != null) {
            ApiResponse newResponse = new ApiResponse()
                    .description(existingResponse.getDescription())
                    .content(existingResponse.getContent());

            responses.addApiResponse(responseCodeKey, newResponse);
            responses.remove("200");
        }
    }

    @SneakyThrows
    private <T> Schema<T> createWrappedSchema(Schema<T> originalSchema,
                                              Class<?> type,
                                              String wrapFieldName,
                                              CommonResponseCode responseCode
    ) {
        Schema<T> wrappedSchema = new Schema<>();

        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();

            Schema<?> fieldSchema = switch (fieldName) {
                case "status" -> new Schema<>().example(responseCode.getHttpStatus().toString());
                case "msg" -> new Schema<>().example(responseCode.getData());
                case "data" -> originalSchema;
                case "code" -> new Schema<>().example(responseCode.name());
                default -> new Schema<>();
            };

            wrappedSchema.addProperty(fieldName, fieldSchema);
        }

        wrappedSchema.addProperty(wrapFieldName, originalSchema);

        return wrappedSchema;
    }

    private void addExceptionSchema(Operation operation, CommonExceptionCode exceptionCode) {
        ApiResponses responses = operation.getResponses();
        String responseCodeKey = String.valueOf(exceptionCode.getHttpStatus().value());

        ApiResponse response = new ApiResponse().description(exceptionCode.getData());
        Content content = new Content();
        MediaType mediaType = new MediaType();
        mediaType.setSchema(createExceptionSchema(exceptionCode));
        content.addMediaType("application/json", mediaType);
        response.setContent(content);

        responses.addApiResponse(responseCodeKey + "_" + exceptionCode.name(), response);
    }

    private Schema<?> createExceptionSchema(CommonExceptionCode exceptionCode) {
        Schema<?> exceptionSchema = new Schema<>();
        exceptionSchema.addProperty("status", new Schema().example(exceptionCode.getHttpStatus().toString()));
        exceptionSchema.addProperty("code", new Schema<>().example(exceptionCode.name()));
        exceptionSchema.addProperty("data", new Schema<>().example(exceptionCode.getData()));
        return exceptionSchema;
    }
}