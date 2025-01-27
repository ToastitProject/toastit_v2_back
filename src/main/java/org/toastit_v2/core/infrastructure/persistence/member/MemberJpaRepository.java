package org.toastit_v2.core.infrastructure.persistence.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastit_v2.core.domain.member.Member;

public interface MemberJpaRepository extends JpaRepository<Member, String> {
}
