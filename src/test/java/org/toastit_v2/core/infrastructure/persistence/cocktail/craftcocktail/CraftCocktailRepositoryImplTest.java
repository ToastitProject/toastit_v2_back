package org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.toastit_v2.common.exception.custom.CustomCraftCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;
import org.toastit_v2.core.domain.member.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CraftCocktailRepositoryImplTest {

    @Mock
    private CraftCocktailJpaRepository repository;

    @InjectMocks
    private CraftCocktailRepositoryImpl craftCocktailRepository;

    private CraftCocktail craftCocktail;
    private Member testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = Member.builder()
                .userId("testUserId")
                .password("password123")
                .profile(null)
                .authority(null)
                .registerDate(null)
                .modifyDate(null)
                .build();

        craftCocktail = CraftCocktail.builder()
                .id(1L)
                .title("모히또")
                .description("상큼한 라임이 들어간 칵테일")
                .recipe("라임, 민트, 럼, 설탕, 탄산수")
                .ingredients("라임, 민트, 럼")
                .user(testUser)
                .build();
    }

    @Test
    void 칵테일_ID로_조회_성공() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        //when
        Optional<CraftCocktail> result = craftCocktailRepository.findById(1L);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("모히또");
    }

    @Test
    void 비활성화된_칵테일은_조회되지_않음() {
        //given
        craftCocktail.setDeleted(true);
        when(repository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        //when
        Optional<CraftCocktail> result = craftCocktailRepository.findById(1L);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void 모든_칵테일_조회_비활성화_제외() {
        // given
        CraftCocktail activeCocktail = craftCocktail;
        CraftCocktail deletedCocktail = CraftCocktail.builder()
                .id(2L)
                .title("마가리타")
                .description("멕시코 대표 칵테일")
                .recipe("데킬라, 라임 주스, 트리플 섹")
                .ingredients("데킬라, 라임, 트리플 섹")
                .user(testUser)
                .build();
        deletedCocktail.setDeleted(true);

        when(repository.findAll()).thenReturn(List.of(activeCocktail, deletedCocktail));

        // when
        List<CraftCocktail> result = craftCocktailRepository.findAll();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("모히또");
    }

    @Test
    void 칵테일_저장_성공() {
        //given
        when(repository.save(craftCocktail)).thenReturn(craftCocktail);

        //when
        CraftCocktail savedCocktail = craftCocktailRepository.save(craftCocktail);

        //then
        assertThat(savedCocktail).isEqualTo(craftCocktail);
    }

    @Test
    void 존재하지_않는_칵테일_업데이트_시_예외_발생() {
        //given
        when(repository.existsById(1L)).thenReturn(false);

        //when & then
        assertThatThrownBy(() -> craftCocktailRepository.update(craftCocktail))
                .isInstanceOf(CustomCraftCocktailException.class)
                .hasMessageContaining(ExceptionCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 칵테일_삭제_처리() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        //when
        craftCocktailRepository.deleteById(1L);

        //then
        assertThat(craftCocktail.isDisplay()).isTrue();
        verify(repository, times(1)).save(craftCocktail);
    }

    @Test
    void 존재하지_않는_칵테일_삭제_시_예외_발생() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> craftCocktailRepository.deleteById(1L))
                .isInstanceOf(CustomCraftCocktailException.class)
                .hasMessageContaining(ExceptionCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 칵테일_신고_처리() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        // when
        craftCocktailRepository.reportById(1L);

        // then
        assertThat(craftCocktail.isDisplay()).isTrue();
        verify(repository, times(1)).save(craftCocktail);
    }

}
