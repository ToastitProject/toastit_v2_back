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
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktailReview;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail.CraftCocktailReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CraftCocktailReviewServiceImpl implements CraftCocktailReviewService {

    private final CraftCocktailReviewRepository reviewRepository;
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

    @Override
    public CraftCocktailReview saveReview(Long craftCocktailId, CraftCocktailReview review) {
        Member authenticatedUser = getAuthenticatedUser();
        CraftCocktail craftCocktail = craftCocktailRepository.findById(craftCocktailId)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.FAIL_CREATE_CRAFT_COCKTAIL));

        review.setCraftCocktail(craftCocktail);
        review.setUser(authenticatedUser);
        return reviewRepository.save(review);
    }

    @Override
    public CraftCocktailReview updateReview(Long reviewId, CraftCocktailReview review) {
        Member authenticatedUser = getAuthenticatedUser();
        CraftCocktailReview existingComment = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.COMMENT_NOT_FOUND));

        if (!existingComment.getUser().equals(authenticatedUser)) {
            throw new CustomCraftCocktailException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        existingComment.setContent(review.getContent());
        return reviewRepository.save(existingComment);
    }

    @Override
    public void deleteReview(Long commentId) {
        Member authenticatedUser = getAuthenticatedUser();
        CraftCocktailReview existingComment = reviewRepository.findById(commentId)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.COMMENT_NOT_FOUND));

        if (!existingComment.getUser().equals(authenticatedUser)) {
            throw new CustomCraftCocktailException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        reviewRepository.deleteById(commentId);
    }

    @Override
    public List<CraftCocktailReview> getReviewsByCraftCocktailId(Long craftCocktailId) {
        return reviewRepository.findByCraftCocktailId(craftCocktailId);
    }

}
