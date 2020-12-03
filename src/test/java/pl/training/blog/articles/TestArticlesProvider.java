package pl.training.blog.articles;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestArticlesProvider {

    public static Article getPublishedArticle(String contents, LocalDateTime publicationDate) {
        var article = new Article(UUID.randomUUID(), contents);
        article.setState(ArticleState.PUBLISHED);
        article.setPublicationDate(publicationDate);
        return article;
    }

}
