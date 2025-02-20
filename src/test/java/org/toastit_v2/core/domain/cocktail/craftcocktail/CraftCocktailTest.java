package org.toastit_v2.core.domain.cocktail.craftcocktail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.domain.member.MemberProfile;
import org.toastit_v2.core.domain.auth.security.Authority;
import org.toastit_v2.common.generator.date.DateTimeGenerator;
import org.toastit_v2.common.generator.nickname.NicknameGenerator;
import org.toastit_v2.common.generator.password.PasswordEncodeGenerator;
import org.toastit_v2.core.ui.member.payload.request.SignUpRequest;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CraftCocktailTest {

    private Member testUser;
    private DateTimeGenerator dateTimeGenerator;
    private NicknameGenerator nicknameGenerator;
    private PasswordEncodeGenerator passwordEncoder;

    @BeforeEach
    void setUp() {
        dateTimeGenerator = mock(DateTimeGenerator.class);
        nicknameGenerator = mock(NicknameGenerator.class);
        passwordEncoder = mock(PasswordEncodeGenerator.class);

        when(dateTimeGenerator.generate()).thenReturn(LocalDateTime.now());
        when(nicknameGenerator.generate()).thenReturn("TestNickname");
        when(passwordEncoder.generate("password123")).thenReturn("encodedPassword123");

        testUser = Member.builder()
                .userId("testUserId") //일단 string으로?
                .password("password123")
                .profile(MemberProfile.create("test@example.com", "testUser"))
                .authority(Authority.USER)
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();
    }

    @Test
    void CraftCocktail_객체_생성_테스트() {
        // given
        CraftCocktail craftCocktail = CraftCocktail.builder()
                .title("모히또")
                .description("상큼한 라임이 들어간 칵테일")
                .recipe("라임, 민트, 럼, 설탕, 탄산수")
                .ingredients("라임, 민트, 럼")
                .user(testUser)
                .build();

        // then
        assertThat(craftCocktail).isNotNull();
        assertThat(craftCocktail.getTitle()).isEqualTo("모히또");
        assertThat(craftCocktail.getDescription()).isEqualTo("상큼한 라임이 들어간 칵테일");
        assertThat(craftCocktail.getRecipe()).isEqualTo("라임, 민트, 럼, 설탕, 탄산수");
        assertThat(craftCocktail.getIngredients()).isEqualTo("라임, 민트, 럼");
        assertThat(craftCocktail.getUser()).isEqualTo(testUser);
        assertThat(craftCocktail.isDisplay()).isFalse(); // 기본값 검증
    }

    @Test
    void 신고된_칵테일은_비활성화된다() {
        // given
        CraftCocktail craftCocktail = CraftCocktail.builder()
                .title("마가리타")
                .description("멕시코 대표 칵테일")
                .recipe("데킬라, 라임 주스, 트리플 섹")
                .ingredients("데킬라, 라임, 트리플 섹")
                .user(testUser)
                .build();

        // when
        craftCocktail.report();

        // then
        assertThat(craftCocktail.isDisplay()).isTrue();
    }

    @Test
    void 칵테일_삭제_처리_테스트() {
        // given
        CraftCocktail craftCocktail = CraftCocktail.builder()
                .title("블루 라군")
                .description("푸른빛이 아름다운 칵테일")
                .recipe("보드카, 블루 큐라소, 레몬 주스")
                .ingredients("보드카, 블루 큐라소, 레몬")
                .user(testUser)
                .build();

        // when
        craftCocktail.setDeleted(true);

        // then
        assertThat(craftCocktail.isDisplay()).isTrue();
    }
}

