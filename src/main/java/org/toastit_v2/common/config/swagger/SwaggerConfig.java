package org.toastit_v2.common.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import lombok.SneakyThrows;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.toastit_v2.common.annotation.swagger.ApiExceptionResponse;
import org.toastit_v2.common.annotation.swagger.ApiRequestBody;
import org.toastit_v2.common.annotation.swagger.ApiSuccessResponse;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.response.code.SuccessCode;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "Mixie V2 API",
                version = "2.0",
                description = "칵테일 API 명세서"
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            ApiSuccessResponse annotation = handlerMethod.getMethodAnnotation(ApiSuccessResponse.class);
            SuccessCode successCode = Optional.ofNullable(annotation)
                    .map(ApiSuccessResponse::value)
                    .orElse(SuccessCode.SUCCESS);
            this.applyResponseSchemaWrapper(operation, SuccessResponse.class, "data", successCode);

            Optional.ofNullable(handlerMethod.getMethodAnnotation(ApiExceptionResponse.class))
                    .map(ApiExceptionResponse::value)
                    .ifPresent(exceptionCodes -> {
                        for (ExceptionCode exceptionCode : exceptionCodes) {
                            addExceptionSchema(operation, exceptionCode);
                        }
                    });

            for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
                ApiRequestBody apiRequestBody = methodParameter.getMethodAnnotation(ApiRequestBody.class);
                if (Objects.nonNull(apiRequestBody)) {
                    RequestBody requestBody = new RequestBody()
                            .description(apiRequestBody.description())
                            .required(apiRequestBody.required());

                    Content content = getContent(apiRequestBody);
                    requestBody.setContent(content);
                    operation.setRequestBody(requestBody);
                }
            }

            operation.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
            return operation;
        };
    }

    private Content getContent(ApiRequestBody apiRequestBody) {
        Content content = new Content();
        MediaType mediaType = new MediaType();

        if (apiRequestBody.content().length > 0 && Objects.nonNull(apiRequestBody.content()[0])) {
            Schema<?> schema = new Schema<>();
            Class<?> implementationClass = apiRequestBody.content()[0].schema().implementation();
            schema.set$ref(implementationClass.getSimpleName());
            mediaType.setSchema(schema);
        }

        content.addMediaType("application/json", mediaType);
        return content;
    }

    private void applyResponseSchemaWrapper(Operation operation,
                                            Class<?> type,
                                            String wrapFieldName,
                                            SuccessCode successCode
    ) {
        ApiResponses responses = operation.getResponses();
        String responseCode = String.valueOf(
                successCode.getHttpStatus().value()
        );

        if (isNonSuccessCode(responseCode)) {
            manageNon200Response(responses, responseCode);
        }

        ApiResponse response = responses.computeIfAbsent(String.valueOf(successCode.getHttpStatus().value()), key -> new ApiResponse());
        response.setDescription(successCode.getMessage());
        Content content = response.getContent();

        if (content != null) {
            content.keySet().forEach(mediaTypeKey -> {
                MediaType mediaType = content.get(mediaTypeKey);
                if (mediaType != null) {
                    mediaType.setSchema(createWrappedSchema(mediaType.getSchema(), type, wrapFieldName, successCode));
                }
            });
        }
    }

    private boolean isNonSuccessCode(String responseCode) {
        return !responseCode.equals("200");
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
                                              SuccessCode successCode
    ) {
        Schema<T> wrappedSchema = new Schema<>();

        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();

            Schema<?> fieldSchema = switch (fieldName) {
                case "result" -> originalSchema;
                case "httpStatus" -> new Schema<>().example(successCode.getHttpStatus().toString());
                case "resultMessage" -> new Schema<>().example(successCode.getMessage());
                case "resultCode" -> new Schema<>().example(successCode.getResultCode());
                default -> new Schema<>();
            };

            wrappedSchema.addProperty(fieldName, fieldSchema);
        }

        wrappedSchema.addProperty(wrapFieldName, originalSchema);

        return wrappedSchema;
    }

    private void addExceptionSchema(Operation operation, ExceptionCode exceptionCode) {
        ApiResponses responses = operation.getResponses();
        String responseCode = String.valueOf(
                exceptionCode.getHttpStatus().value()
        );

        ApiResponse response = new ApiResponse().description(exceptionCode.getMessage());
        Content content = new Content();
        MediaType mediaType = new MediaType();
        mediaType.setSchema(createExceptionSchema(exceptionCode));
        content.addMediaType("application/json", mediaType);
        response.setContent(content);

        responses.addApiResponse(responseCode + "_" + exceptionCode.name(), response);
    }

    private Schema<?> createExceptionSchema(ExceptionCode exceptionCode) {
        Schema<?> exceptionSchema = new Schema<>();
        exceptionSchema.addProperty("http_status", new Schema().example(exceptionCode.getHttpStatus().toString()));
        exceptionSchema.addProperty("result_message", new Schema<>().example(exceptionCode.getMessage()));
        exceptionSchema.addProperty("result_code", new Schema<>().example(exceptionCode.getResultCode()));
        return exceptionSchema;
    }

}