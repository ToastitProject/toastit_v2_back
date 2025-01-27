package org.toastit_v2.core.application.auth.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.common.exception.custom.CustomMemberException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.member.user.port.MemberRepository;
import org.toastit_v2.core.domain.auth.security.CustomUserDetails;
import org.toastit_v2.core.domain.member.Member;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
        final Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomMemberException(ExceptionCode.MEMBER_NOT_FOUND));

        return CustomUserDetails.from(member);
    }

}
