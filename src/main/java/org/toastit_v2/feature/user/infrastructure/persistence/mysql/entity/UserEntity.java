package org.toastit_v2.feature.user.infrastructure.persistence.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.toastit_v2.core.common.infrastructure.persistence.entity.AuditingFields;
import org.toastit_v2.feature.user.domain.Authority;
import org.toastit_v2.feature.user.domain.User;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public static UserEntity fromModel(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .imageUrl(user.getImageUrl())
                .authority(user.getAuthority())
                .build();
    }

    public User toModel() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .imageUrl(this.imageUrl)
                .authority(this.authority)
                .build();
    }

}
