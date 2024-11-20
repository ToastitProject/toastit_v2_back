package org.toastit_v2.feature.email.application.port;

public interface EmailAuthCodeRepository {

    String findByEmail(String email);

    void save(String email, String code);

    void delete(String email);

}
