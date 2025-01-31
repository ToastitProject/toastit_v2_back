package org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.common.exception.custom.CustomCraftCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.cocktail.craftcocktail.port.CraftCocktailRepository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CraftCocktailRepositoryImpl implements CraftCocktailRepository {

    private final CraftCocktailJpaRepository repository;

    @Override
    public Optional<CraftCocktail> findById(Long id) {
        return repository.findById(id)
                .filter(entity -> !entity.isDeleted()); // 비활성화된 게시물 제외
    }

    @Override
    public List<CraftCocktail> findAll() {
        return repository.findAll()
                .stream()
                .filter(entity -> !entity.isDeleted()) // 비활성화된 게시물 제외
                .toList();
    }

    @Override
    public CraftCocktail save(CraftCocktail craftCocktail) {
        return repository.save(craftCocktail);
    }

    @Override
    public CraftCocktail update(CraftCocktail craftCocktail) {

        if (!repository.existsById(craftCocktail.getId())) {
            throw new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND);
        }

        return repository.save(craftCocktail);
    }

    @Override
    public void deleteById(Long id) {
        CraftCocktail entity = repository.findById(id)
                .orElseThrow(
                        () -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND)
        );

        entity.setDeleted(true); // 실제 삭제 대신 비활성화
        repository.save(entity);
    }

}
