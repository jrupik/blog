package pl.training.blog;

import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexpRankingServiceTest {

    private final RankingService rankingService = new RegexpRankingService();

    @Test
    void the_rank_of_the_article_should_be_equal_to_the_number_of_occurrences_of_the_searched_word() {
        var article = new Article(randomUUID(), "Java language");
        var rank = rankingService.getRank("Java", article);
        assertEquals(1, rank.getValue());
    }

    @Test
    void the_rank_of_the_article_does_not_depend_on_the_case_of_the_searched_word() {
        var article = new Article(randomUUID(), "Java language");
        assertEquals(rankingService.getRank("java", article),  rankingService.getRank("Java", article));
    }

}
