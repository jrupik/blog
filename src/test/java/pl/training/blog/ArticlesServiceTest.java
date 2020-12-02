package pl.training.blog;

import lombok.Getter;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticlesServiceTest {

    private final Article testArticle = new Article(UUID.randomUUID());
    private final ArticlesRepository articlesRepositoryMock = mock(ArticlesRepository.class);

    private static class ArticlesRepositoryStub implements  ArticlesRepository {

        @Getter
        private boolean isSaved;

        @Override
        public Article save(Article article) {
            isSaved = true;
            return article;
        }

        @Override
        public List<Article> getHistoryByUUID(UUID articleUUID) {
            return null;
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
    void updated_article_should_saved() {
        var uuid = UUID.randomUUID();
        var article = new Article(uuid);
        var modifiedArticle = new Article(uuid);
        when(articlesRepositoryMock.save(any(Article.class))).then(returnsFirstArg());
        // when(articlesRepositoryMock.getHistoryByUUID(any(UUID.class))).thenReturn(List.of(modifiedArticle, article));
        var articleService = new ArticlesService(articlesRepositoryMock);
        articleService.add(article);

        articleService.update(modifiedArticle);

        var updatesHistory = articleService.getHistory(article.getUuid());
        verify(articlesRepositoryMock).save(modifiedArticle);
        //MatcherAssert.assertThat(updatesHistory, hasItems(article, modifiedArticle));
    }

}
