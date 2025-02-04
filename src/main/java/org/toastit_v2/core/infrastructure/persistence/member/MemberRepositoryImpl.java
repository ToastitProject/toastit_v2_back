package org.toastit_v2.core.infrastructure.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.application.member.port.MemberRepository;
import org.toastit_v2.core.domain.member.Member;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository repository;

    @Override
    public Member save(final Member member) {
        return repository.save(member);
    }

    @Override
    public Optional<Member> findById(final String userId) {
        return repository.findById(userId);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return repository.findById(email);
    }

    @Override
    public void delete(final Member member) {
        repository.delete(member);
    }

}
