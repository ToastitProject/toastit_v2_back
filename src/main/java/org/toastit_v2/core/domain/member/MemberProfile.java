package org.toastit_v2.core.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile {

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "nickname")
    private String nickname;

    private String content;

    private String thumbnail;

    @Builder
    private MemberProfile(final String userEmail, final String nickname, final String content, final String thumbnail) {
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public static MemberProfile create(final String userEmail, final String nickname) {
        return new MemberProfile(userEmail, nickname, null, null);
    }

}
