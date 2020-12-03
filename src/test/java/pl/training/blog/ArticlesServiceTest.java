package pl.training.blog;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticlesServiceTest {

    private final ArticlesRepository articlesRepositoryMock = mock(ArticlesRepository.class);
    private final RankingService rankingServiceMock = mock(RankingService.class);
    private final ArticlesService articleService = new ArticlesService(articlesRepositoryMock, rankingServiceMock);

    private static class ArticlesRepositoryStub implements ArticlesRepository {

        @Getter
        private boolean isSaved;

        @Override
        public Article save(Article article) {
            isSaved = true;
            return article;
        }

        @Override
        public List<Article> findAll() {
            return null;
        }

    }

    @BeforeEach
    void setup() {
        when(articlesRepositoryMock.save(any(Article.class))).then(returnsFirstArg());
    }

    @Test
    void added_article_should_be_saved() {
        var article = new Article(randomUUID(), "");
        var articlesRepositoryStub = new ArticlesRepositoryStub();
        var articleService = new ArticlesService(articlesRepositoryStub, rankingServiceMock);
        articleService.add(article);
        assertTrue(articlesRepositoryStub.isSaved);
    }

    @Test
    void updated_article_should_be_saved() {
        var uuid = randomUUID();
        var article = new Article(uuid, "");
        var modifiedArticle = new Article(uuid, "Java language");
        articleService.add(article);
        articleService.update(modifiedArticle);
        verify(articlesRepositoryMock).save(modifiedArticle);
    }

    @Test
    void published_article_should_be_saved() {
        var article = new Article(randomUUID(), "");
        articleService.publish(article);
        verify(articlesRepositoryMock).save(article);
    }

    @Test
    void state_of_added_article_is_set_to_draft() {
        var article = new Article(randomUUID(), "");
        article.setState(ArticleState.PUBLISHED);
        var addedArticle = articleService.add(article);
        assertEquals(ArticleState.DRAFT, addedArticle.getState());
    }

    @Test
    void state_of_updated_article_is_set_to_draft() {
        var article = articleService.add(new Article(randomUUID(), ""));
        article.setState(ArticleState.PUBLISHED);
        articleService.update(article);
        assertEquals(ArticleState.DRAFT, article.getState());
    }

    @Test
    void state_of_published_article_is_set_to_finished() {
        var article = articleService.add(new Article(randomUUID(), ""));
        articleService.publish(article);
        assertEquals(ArticleState.PUBLISHED, article.getState());
    }

    @Test
    void articles_searched_by_the_keyword_are_sorted_by_rank_in_descending_order() {
        var keyword = "java";
        var firstArticle = new Article(randomUUID(), "java");
        var secondArticle = new Article(randomUUID(), "java java");
        when(rankingServiceMock.getRank(keyword, firstArticle)).thenReturn(new Rank(1L, firstArticle));
        when(rankingServiceMock.getRank(keyword, secondArticle)).thenReturn(new Rank(2L, secondArticle));
        when(articlesRepositoryMock.findAll()).thenReturn(List.of(firstArticle, secondArticle));
        assertEquals(List.of(secondArticle, firstArticle), articleService.findArticlesByKeyword(keyword));
    }

}
