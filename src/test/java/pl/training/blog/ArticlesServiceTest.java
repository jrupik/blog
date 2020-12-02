package pl.training.blog;

import lombok.Getter;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ArticlesServiceTest {

    private final Article testArticle = new Article(1L);
    private final ArticlesRepository articlesRepositoryMock = mock(ArticlesRepository.class);

    private static class ArticlesRepositoryStub implements  ArticlesRepository {

        @Getter
        private boolean isSaved;

        @Override
        public Article save(Article article) {
            isSaved = true;
            return article;
        }

    }

    @Test
    void added_articles_should_be_saved () {
        var articlesRepositoryStub = new ArticlesRepositoryStub();
        var articleService = new ArticlesService(articlesRepositoryStub);
        articleService.add(testArticle);
        assertTrue(articlesRepositoryStub.isSaved);
    }

    @Test
    void article_modification_should_add_historical_values_to_history() {
        var articleService = new ArticlesService(articlesRepositoryMock);
        var article = articleService.add(testArticle);
        article.setContents("new text");
        articleService.update(article);
        List<Article> updatesHistory = articleService.getHistory(article.getId());
        MatcherAssert.assertThat(updatesHistory, hasItems(testArticle, article));
    }

}

// equlas