package org.toastit_v2.core.infrastructure.persistence.cocktail.basecocktail.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.toastit_v2.core.common.application.config.querydsl.ExcludeFromJpaRepositories;
import org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument;
import org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.custom.CustomCocktailRepository;

@ExcludeFromJpaRepositories
public interface CocktailMongoRepository extends MongoRepository<CocktailDocument, ObjectId>, CustomCocktailRepository {
}

