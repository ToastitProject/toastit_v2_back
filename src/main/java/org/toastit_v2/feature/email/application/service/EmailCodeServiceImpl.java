package org.toastit_v2.feature.email.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.feature.email.application.port.EmailAuthCodeRepository;

@Service
@RequiredArgsConstructor
public class EmailCodeServiceImpl implements EmailCodeService {

    private final EmailAuthCodeRepository emailAuthCodeRepository;

    @Override
    public String findByEmail(String email) {
        return emailAuthCodeRepository.findByEmail(email);
    }

    @Override
    public void save(String email, String code) {
        emailAuthCodeRepository.save(email, code);
    }

    @Override
    public void delete(String email) {
        emailAuthCodeRepository.delete(email);
    }

}
