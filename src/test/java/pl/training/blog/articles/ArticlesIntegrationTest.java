package pl.training.blog.articles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.training.blog.BlogApplication;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.training.blog.articles.TestArticlesProvider.getPublishedArticle;

@SpringBootTest(classes = BlogApplication.class)
@ExtendWith(SpringExtension.class)
public class ArticlesIntegrationTest {

    private final Article firstArticle = getPublishedArticle("java", LocalDateTime.now());
    private final Article secondArticle = getPublishedArticle("java java", LocalDateTime.now().plusDays(1));

    @Autowired
    private ArticlesService articlesService;

    @BeforeEach
    void setup() {
        articlesService.add(firstArticle);
        articlesService.add(secondArticle);
    }

    @Test
    void articles_searched_by_the_keyword_are_sorted_by_rank_in_descending_order() {
        var result = articlesService.findArticlesByKeyword("java").stream()
                .map(Article::getUuid)
                .collect(toList());
        assertEquals(List.of(secondArticle.getUuid(), firstArticle.getUuid()), result);
    }

}
