package org.toastit_v2.core.ui.member.payload.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.domain.member.MemberProfile;

@Schema(
        description = "회원 정보 응답 Response"
)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfileResponse(
        String userId,
        MemberProfile memberProfile
) {

    public static ProfileResponse from(Member member) {
        return new ProfileResponse(member.getUserId(), member.getProfile());
    }

}
