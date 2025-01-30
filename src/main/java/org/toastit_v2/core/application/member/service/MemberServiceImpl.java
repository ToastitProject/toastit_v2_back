package org.toastit_v2.core.application.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.common.exception.custom.CustomMemberException;
import org.toastit_v2.common.generator.date.DateTimeGeneratorImpl;
import org.toastit_v2.common.generator.nickname.NicknameGeneratorImpl;
import org.toastit_v2.common.generator.password.PasswordEncodeGeneratorImpl;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.auth.token.service.TokenService;
import org.toastit_v2.core.application.member.port.MemberRepository;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.ui.member.payload.request.LoginRequest;
import org.toastit_v2.core.ui.member.payload.request.SignUpRequest;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    @Transactional
    public Member signUp(final SignUpRequest request) {
        if (repository.findById((request.userId())).isPresent()) {
            throw new CustomMemberException((ExceptionCode.MEMBER_DUPLICATE));
        }
        final Member member = Member.create(request, new DateTimeGeneratorImpl(), new NicknameGeneratorImpl());
        return repository.save(member.passwordEncode(new PasswordEncodeGeneratorImpl(passwordEncoder)));
    }

    @Override
    @Transactional(readOnly = true)
    public void checkUserIdDuplicate(final String userId) {
        if (repository.findById(userId).isPresent()) {
            throw new CustomMemberException((ExceptionCode.MEMBER_DUPLICATE));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByUserId(final String userId) {
        return repository.findById(userId).orElseThrow(
                () -> new CustomMemberException(ExceptionCode.NOT_EXISTS_USERID)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public String login(final LoginRequest request) {
        final Member member = repository.findById(request.userId()).orElseThrow(
                () -> new CustomMemberException(ExceptionCode.NOT_EXISTS_USERID)
        );
        member.validatePasswordMatch(member.getPassword());
        return tokenService.createAccessToken(member);
    }

    @Override
    @Transactional(readOnly = true)
    public void logout(final String userId) {
        Member member = repository.findById(userId).orElseThrow(
                () -> new CustomMemberException(ExceptionCode.NOT_EXISTS_USERID)
        );
        tokenService.logout(member);
    }

}
