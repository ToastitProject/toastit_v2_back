package org.toastit_v2.feature.craftcockatil.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.craftcockatil.application.port.CraftCocktailRepository;
import org.toastit_v2.feature.craftcockatil.domain.CraftCocktail;

import java.util.List;

@Service
@Transactional
public class CraftCocktailServiceImpl implements CraftCocktailService {

    private final CraftCocktailRepository craftCocktailRepository;

    public CraftCocktailServiceImpl(CraftCocktailRepository craftCocktailRepository) {
        this.craftCocktailRepository = craftCocktailRepository;
    }

    public CraftCocktail findById(Long id) {
        return craftCocktailRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
    }

    public List<CraftCocktail> findAll() {
        return craftCocktailRepository.findAll();
    }

    public CraftCocktail save(CraftCocktail craftCocktail) {
        return craftCocktailRepository.save(craftCocktail);
    }

    public CraftCocktail update(CraftCocktail craftCocktail) {
        craftCocktailRepository.findById(craftCocktail.getId())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        return craftCocktailRepository.update(craftCocktail);
    }

    public void deleteById(Long id) {
        craftCocktailRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        craftCocktailRepository.deleteById(id);
    }

}
