package org.toastit_v2.common.generator.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.toastit_v2.core.domain.member.Member;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * 지정된 회원 정보를 기반으로 JWT 토큰을 생성하는 클래스입니다.
 */
@Component
public class JwtGenerator {

    /**
     * 지정된 회원 정보로 JWT 토큰을 생성합니다.
     * 
     * @param secretKey 토큰 서명에 사용할 SecretKey
     * @param expiration 토큰 만료 시간(밀리초)
     * @param member 토큰을 생성할 회원 정보
     * @return 서명된 JWT 토큰 문자열
     */
    public String generate(final SecretKey secretKey, final long expiration, final Member member) {
        final Claims claims = create(member);

        return Jwts.builder()
                .subject(member.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(expiration))
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }
    
    /**
     * 회원 정보를 기반으로 JWT 클레임을 생성합니다.
     * 
     * @param member 클레임에 포함할 회원 정보
     * @return 회원 ID와 권한이 포함된 Claims 객체
     */
    private Claims create(Member member) {
        return Jwts.claims()
                .add("id", member.getUserId())
                .add("role", member.getAuthority())
                .build();
    }

}
