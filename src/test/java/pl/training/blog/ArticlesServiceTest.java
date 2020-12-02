package pl.training.blog;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArticlesServiceTest {

    private final Article testArticle = new Article();

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

}
