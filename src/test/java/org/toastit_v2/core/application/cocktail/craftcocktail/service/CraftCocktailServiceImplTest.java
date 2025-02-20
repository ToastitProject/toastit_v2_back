package org.toastit_v2.core.application.cocktail.craftcocktail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.toastit_v2.common.exception.custom.CustomCraftCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.cocktail.craftcocktail.port.CraftCocktailRepository;
import org.toastit_v2.core.application.member.port.MemberRepository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;
import org.toastit_v2.core.domain.member.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CraftCocktailServiceImplTest {

    @Mock
    private CraftCocktailRepository craftCocktailRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CraftCocktailServiceImpl craftCocktailService;

    private Member authenticatedUser;
    private CraftCocktail craftCocktail;


    /**
     *  UserDetails를 모킹하여 principal로 사용
     *  memberRepository에서 인증된 사용자를 찾도록 모킹
     *  테스트용 칵테일 (작성자: authenticatedUser) 포함
     *
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authenticatedUser = Member.builder()
                .userId("testUserId")
                .password("password123")
                .build();


        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(authenticatedUser.getUserId());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(memberRepository.findById(authenticatedUser.getUserId()))
                .thenReturn(Optional.of(authenticatedUser));

        craftCocktail = CraftCocktail.builder()
                .id(1L)
                .title("모히또")
                .description("상큼한 라임이 들어간 칵테일")
                .recipe("라임, 민트, 럼, 설탕, 탄산수")
                .ingredients("라임, 민트, 럼")
                .user(authenticatedUser)
                .build();
    }

    @Test
    void 칵테일_ID로_조회_성공() {
        // given
        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        // when
        CraftCocktail result = craftCocktailService.findById(1L);

        // then
        assertThat(result.getTitle()).isEqualTo("모히또");
    }

    @Test
    void 칵테일_ID로_조회_실패_존재하지_않으면() {
        // given
        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> craftCocktailService.findById(1L))
                .isInstanceOf(CustomCraftCocktailException.class)
                .hasMessageContaining(ExceptionCode.FAIL_CREATE_CRAFT_COCKTAIL.getMessage());
    }

    @Test
    void 모든_칵테일_조회() {
        // given
        CraftCocktail anotherCocktail = CraftCocktail.builder()
                .id(2L)
                .title("마가리타")
                .description("멕시코 대표 칵테일")
                .recipe("데킬라, 라임 주스, 트리플 섹")
                .ingredients("데킬라, 라임, 트리플 섹")
                .user(authenticatedUser)
                .build();

        when(craftCocktailRepository.findAll()).thenReturn(List.of(craftCocktail, anotherCocktail));

        // when
        List<CraftCocktail> result = craftCocktailService.findAll();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("모히또");
        assertThat(result.get(1).getTitle()).isEqualTo("마가리타");
    }

    @Test
    void 칵테일_저장_성공() {
        // given
        when(craftCocktailRepository.save(any(CraftCocktail.class))).thenReturn(craftCocktail);

        // when
        CraftCocktail savedCocktail = craftCocktailService.save(craftCocktail);

        // then
        assertThat(savedCocktail).isEqualTo(craftCocktail);
        // 저장 시 인증된 사용자가 작성자로 설정되었는지 확인
        assertThat(savedCocktail.getUser()).isEqualTo(authenticatedUser);
    }

    @Test
    void 칵테일_업데이트_성공() {
        // given
        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.of(craftCocktail));
        when(craftCocktailRepository.update(any(CraftCocktail.class))).thenReturn(craftCocktail);

        // when
        CraftCocktail updatedCocktail = craftCocktailService.update(craftCocktail);

        // then
        assertThat(updatedCocktail).isEqualTo(craftCocktail);
    }

    @Test
    void 칵테일_업데이트_실패_권한_없음() {
        // given: 칵테일의 작성자를 다른 사용자로 설정하여 권한 없음 상황을 만듦
        Member differentUser = Member.builder().userId("differentUser").build();
        CraftCocktail cocktailWithDifferentOwner = CraftCocktail.builder()
                .id(1L)
                .title("모히또")
                .description("상큼한 칵테일")
                .recipe("라임, 민트, 럼")
                .ingredients("라임, 민트, 럼")
                .user(differentUser)
                .build();

        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.of(cocktailWithDifferentOwner));

        // when & then: 업데이트 시 UNAUTHORIZED_ACCESS 예외 발생
        assertThatThrownBy(() -> craftCocktailService.update(craftCocktail))
                .isInstanceOf(CustomCraftCocktailException.class)
                .hasMessageContaining(ExceptionCode.UNAUTHORIZED_ACCESS.getMessage());
    }

    @Test
    void 칵테일_삭제_성공() {
        // given
        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        // when
        craftCocktailService.deleteById(1L);

        // then
        verify(craftCocktailRepository, times(1)).deleteById(1L);
    }

    @Test
    void 칵테일_삭제_실패_존재하지_않음() {
        // given
        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> craftCocktailService.deleteById(1L))
                .isInstanceOf(CustomCraftCocktailException.class)
                .hasMessageContaining(ExceptionCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 칵테일_신고_처리() {
        // given
        when(craftCocktailRepository.findById(1L)).thenReturn(Optional.of(craftCocktail));

        // when
        craftCocktailService.reportById(1L);

        // then
        verify(craftCocktailRepository, times(1)).reportById(1L);
    }
}
