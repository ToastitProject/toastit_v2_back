package org.toastit_v2.feature.craftcockatil.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.craftcockatil.application.port.CraftCocktailRepository;
import org.toastit_v2.feature.craftcockatil.domain.CraftCocktail;

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
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER);
        }

        CraftCocktailEntity updatedEntity = jpaCraftCocktailRepository.save(CraftCocktailEntity.model(craftCocktail));
        return updatedEntity.toModel();
    }

    @Override
    public void deleteById(Long id) {
        CraftCocktailEntity entity = jpaCraftCocktailRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        entity.setDeleted(true); // 실제 삭제 대신 비활성화
        jpaCraftCocktailRepository.save(entity);
    }
    
}
