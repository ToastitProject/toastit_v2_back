package org.toastit_v2.feature.email.application.service;

public interface EmailCodeService {

    String findByEmail(String email);

    void save(String email, String code);

    void delete(String email);

}
