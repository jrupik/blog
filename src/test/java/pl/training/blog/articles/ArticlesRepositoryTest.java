package pl.training.blog.articles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.training.blog.articles.TestArticlesProvider.getPublishedArticle;

@Tag("medium")
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ArticlesRepositoryTest {

    private final Article firstArticle = getPublishedArticle("java", LocalDateTime.now());
    private final Article secondArticle = getPublishedArticle("java java", LocalDateTime.now().plusDays(1));

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ArticlesRepository articlesRepository;

    @BeforeEach
    void setup() {
        entityManager.persist(firstArticle);
        entityManager.persist(secondArticle);
    }

    @Test
    void published_articles_should_be_sorted_by_publication_date() {
        assertEquals(List.of(secondArticle, firstArticle), articlesRepository.findPublished());
    }

}
