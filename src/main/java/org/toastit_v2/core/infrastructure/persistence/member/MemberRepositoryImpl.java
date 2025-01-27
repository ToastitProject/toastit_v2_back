package org.toastit_v2.core.infrastructure.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.application.member.user.port.MemberRepository;
import org.toastit_v2.core.domain.member.Member;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository repository;

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return repository.findById(userId);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findById(email);
    }

    @Override
    public void delete(Member member) {
        repository.delete(member);
    }

}
