/*
package org.toastit_v2.core.infrastructure.persistence.cocktail.basecocktail.mongodb;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.basecocktail.application.port.CocktailRepository;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;
import org.toastit_v2.feature.basecocktail.domain.CocktailSearch;
import org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument;
import org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.MongoCocktailRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CocktailRepositoryImpl implements CocktailRepository {

    private final MongoCocktailRepository mongoRepository;

    @Override
    public Page<Cocktail> search(CocktailSearch searchCondition, Pageable pageable) {
        return mongoRepository.search(searchCondition, pageable)
                .map(org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument::toDomain);
    }

    @Override
    public Optional<Cocktail> findById(ObjectId id) {
        return mongoRepository.findById(id)
                .map(org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument::toDomain);
    }

    @Override
    public List<Cocktail> findByIdIn(List<ObjectId> ids) {
        return mongoRepository.findAllById(ids)
                .stream()
                .map(org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cocktail> findRandom(int count) {
        return mongoRepository.findRandom(count)
                .stream()
                .map(org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Cocktail> findAll(Pageable pageable) {
        Page<org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument> documents = mongoRepository.findAll(pageable);
        return documents.map(org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument::toDomain);
    }

    @Override
    public List<Cocktail> findAllNames() {
        return mongoRepository.findAllNames().stream()
                .map(org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Cocktail save(Cocktail cocktail) {
        org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument document = CocktailDocument.fromDomain(cocktail);
        return mongoRepository.save(document).toDomain();
    }
}

*/
