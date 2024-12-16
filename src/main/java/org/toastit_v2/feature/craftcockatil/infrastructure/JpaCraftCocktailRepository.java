package org.toastit_v2.feature.craftcockatil.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCraftCocktailRepository extends JpaRepository<CraftCocktailEntity, Long> {

}
