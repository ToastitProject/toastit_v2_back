package org.toastit_v2.core.application.cocktail.craftcocktail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.common.exception.custom.CustomCraftCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.cocktail.craftcocktail.port.CraftCocktailRepository;
import org.toastit_v2.core.application.member.port.MemberRepository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;
import org.toastit_v2.core.domain.member.Member;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CraftCocktailServiceImpl implements CraftCocktailService {

    private final CraftCocktailRepository craftCocktailRepository;
    private final MemberRepository memberRepository;

    private Member getAuthenticatedUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> auth.getPrincipal())
                .filter(UserDetails.class::isInstance)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername)
                .flatMap(memberRepository::findById)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND));
    }

    public CraftCocktail findById(Long id) {
        return craftCocktailRepository.findById(id)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.FAIL_CREATE_CRAFT_COCKTAIL));
    }

    public List<CraftCocktail> findAll() {
        return craftCocktailRepository.findAll();
    }

    public CraftCocktail save(CraftCocktail craftCocktail) {
        Member authenticatedUser = getAuthenticatedUser();
        craftCocktail.setUser(authenticatedUser);
        return craftCocktailRepository.save(craftCocktail);
    }

    public CraftCocktail update(CraftCocktail craftCocktail) {
        Member authenticatedUser = getAuthenticatedUser();
        CraftCocktail existingCocktail = craftCocktailRepository.findById(craftCocktail.getId())
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.FAIL_CREATE_CRAFT_COCKTAIL));

        if (!existingCocktail.getUser().equals(authenticatedUser)) {
            throw new CustomCraftCocktailException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        return craftCocktailRepository.update(craftCocktail);
    }


    public void deleteById(Long id) {
        Member authenticatedUser = getAuthenticatedUser();
        CraftCocktail existingCocktail = craftCocktailRepository.findById(id)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND));

        if (!existingCocktail.getUser().equals(authenticatedUser)) {
            throw new CustomCraftCocktailException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        craftCocktailRepository.deleteById(id);
    }


    public void reportById(Long id) {
        craftCocktailRepository.findById(id)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND));

        craftCocktailRepository.reportById(id);
    }

}
