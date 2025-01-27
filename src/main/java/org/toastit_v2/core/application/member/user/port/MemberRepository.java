package org.toastit_v2.core.application.member.user.port;

import org.toastit_v2.core.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByEmail(String email);

    void delete(Member member);

}
