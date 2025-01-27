package org.toastit_v2.core.infrastructure.persistence.cocktail.trendcocktail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrendCocktailJpaRepository extends JpaRepository<TrendCocktailEntity,Long> {

    void save (List<String> list);

}
