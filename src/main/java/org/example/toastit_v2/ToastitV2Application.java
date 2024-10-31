package org.example.toastit_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ToastitV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ToastitV2Application.class, args);
    }
}