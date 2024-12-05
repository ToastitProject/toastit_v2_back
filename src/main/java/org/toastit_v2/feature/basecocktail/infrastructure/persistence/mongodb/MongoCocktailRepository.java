package org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.custom.CustomCocktailRepository;

public interface MongoCocktailRepository extends MongoRepository<CocktailDocument, ObjectId>, CustomCocktailRepository {
}