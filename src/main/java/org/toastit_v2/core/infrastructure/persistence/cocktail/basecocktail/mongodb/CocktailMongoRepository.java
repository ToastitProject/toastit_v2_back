package org.toastit_v2.core.infrastructure.persistence.cocktail.basecocktail.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.toastit_v2.common.annotation.jpa.ExcludeJpaRepository;
import org.toastit_v2.core.infrastructure.persistence.cocktail.basecocktail.mongodb.custom.CustomCocktailRepository;

@ExcludeJpaRepository
public interface CocktailMongoRepository extends MongoRepository<CocktailDocument, ObjectId>, CustomCocktailRepository {
}
