package org.toastit_v2.common.config.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.toastit_v2.common.annotation.jpa.ExcludeJpaRepository;

@EnableJpaRepositories(
        basePackages = "org.toastit_v2",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = ExcludeJpaRepository.class
        )
)
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
