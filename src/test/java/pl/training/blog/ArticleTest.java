package pl.training.blog;

import org.junit.jupiter.api.Test;
import pl.training.blog.articles.Article;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleTest {

    @Test
    void words_count_should_be_equal_to_the_number_of_occurrences_of_the_searched_word() {
        var article = new Article(randomUUID(), "Java language");
        assertEquals(1, article.getWordsCount("Java"));
    }

    @Test
    void words_count_does_not_depend_on_the_case_of_the_searched_word() {
        var article = new Article(randomUUID(), "Java language");
        assertEquals(article.getWordsCount("java"),  article.getWordsCount("Java"));
    }

}
