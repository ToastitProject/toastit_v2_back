package org.toastit_v2.core.domain.cocktail.craftcocktail;

import jakarta.persistence.*;
import lombok.*;
import org.toastit_v2.core.domain.member.Member;

@Entity
@Table(name = "craft_cocktails")
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftCocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String recipe;

    @Column(nullable = false)
    private String ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member user;

    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
