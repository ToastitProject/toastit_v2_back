package org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

@Repository
public interface CraftCocktailJpaRepository extends JpaRepository<CraftCocktail, Long> {

}
