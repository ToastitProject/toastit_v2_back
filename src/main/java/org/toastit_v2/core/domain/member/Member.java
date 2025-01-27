package org.toastit_v2.core.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.toastit_v2.common.exception.custom.CustomMemberException;
import org.toastit_v2.common.generator.date.DateTimeGenerator;
import org.toastit_v2.common.generator.nickname.NicknameGenerator;
import org.toastit_v2.common.generator.password.PasswordEncodeGenerator;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.type.auth.security.Authority;
import org.toastit_v2.core.ui.member.payload.request.SignUpRequest;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "members",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "user_id" }
        )
)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @NotNull
    @Column(name = "user_id")
    private String userId;

    @NotNull
    private String password;

    @Embedded
    private MemberProfile profile;

    @NotNull
    private Authority authority;

    @NotNull
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @NotNull
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    private Member(final String userId, final String password, final MemberProfile profile, final Authority authority, final LocalDateTime registerDate, final LocalDateTime modifyDate) {
        this.userId = userId;
        this.password = password;
        this.profile = profile;
        this.authority = authority;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
    }

    private Member(final String userId, final String password, final String checkPassword, final MemberProfile profile, final Authority authority, final LocalDateTime registerDate, final LocalDateTime modifyDate) {
        this.userId = userId;
        this.password = password;
        validatePasswordMatch(checkPassword);
        this.profile = profile;
        this.authority = authority;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
    }

    public static Member create(final SignUpRequest request, DateTimeGenerator dateTimeGenerator, NicknameGenerator nicknameGenerator) {
        return new Member(
                request.userId(),
                request.password(),
                request.checkPassword(),
                MemberProfile.create(request.userEmail(), nicknameGenerator.generate()),
                Authority.USER,
                dateTimeGenerator.generate(),
                dateTimeGenerator.generate()
        );
    }

    public Member passwordEncode(PasswordEncodeGenerator encoder) {
        return new Member(
                this.userId,
                encoder.generate(this.password),
                this.profile,
                this.authority,
                this.registerDate,
                this.modifyDate
        );
    }

    public void validatePasswordMatch(String password) {
        if (!password.equals(this.password)) {
            throw new CustomMemberException(ExceptionCode.PASSWORD_MISS_MATCH_ERROR);
        }
    }

}
