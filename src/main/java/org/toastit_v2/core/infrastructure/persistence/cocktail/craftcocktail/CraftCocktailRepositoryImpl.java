package org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.common.exception.custom.CustomCraftCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.cocktail.craftcocktail.port.CraftCocktailRepository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

import javax.naming.CommunicationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CraftCocktailRepositoryImpl implements CraftCocktailRepository {

    private final JpaCraftCocktailRepository jpaCraftCocktailRepository;

    @Override
    public Optional<CraftCocktail> findById(Long id) {
        return jpaCraftCocktailRepository.findById(id)
                .filter(entity -> !entity.isDeleted()) // 비활성화된 게시물 제외
                .map(CraftCocktailEntity::toModel);
    }

    @Override
    public List<CraftCocktail> findAll() {
        return jpaCraftCocktailRepository.findAll()
                .stream()
                .filter(entity -> !entity.isDeleted()) // 비활성화된 게시물 제외
                .map(CraftCocktailEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public CraftCocktail save(CraftCocktail craftCocktail) {
        CraftCocktailEntity savedEntity = jpaCraftCocktailRepository.save(CraftCocktailEntity.model(craftCocktail));
        return savedEntity.toModel();
    }

    @Override
    public CraftCocktail update(CraftCocktail craftCocktail) {

        if (!jpaCraftCocktailRepository.existsById(craftCocktail.getId())) {
            throw new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND);
        }

        CraftCocktailEntity updatedEntity = jpaCraftCocktailRepository.save(CraftCocktailEntity.model(craftCocktail));
        return updatedEntity.toModel();
    }

    @Override
    public void deleteById(Long id) {
        CraftCocktailEntity entity = jpaCraftCocktailRepository.findById(id)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND));

        entity.setDeleted(true); // 실제 삭제 대신 비활성화
        jpaCraftCocktailRepository.save(entity);
    }

}
