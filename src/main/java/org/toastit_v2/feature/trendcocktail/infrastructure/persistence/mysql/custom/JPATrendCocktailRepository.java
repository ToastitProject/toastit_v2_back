package org.toastit_v2.feature.trendcocktail.infrastructure.persistence.mysql.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.trendcocktail.infrastructure.persistence.mysql.entity.TrendCocktailEntity;

import java.util.List;

@Repository
public interface JPATrendCocktailRepository extends JpaRepository<TrendCocktailEntity,Long> {

    void save (List<String> list);

}
