package pl.training.blog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ArticlesRepositoryTest {

    private final Article firstArticle = new Article(randomUUID(), "java");
    private final Article secondArticle = new Article(randomUUID(), "java java");

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ArticlesRepository articlesRepository;

    @BeforeEach
    void setup() {
        firstArticle.setPublicationDate(LocalDateTime.now());
        secondArticle.setPublicationDate(LocalDateTime.now().plusDays(1L));
        entityManager.persist(firstArticle);
        entityManager.persist(secondArticle);
    }

    @Test
    void published_articles_should_be_sorted_by_publication_date() {
        assertEquals(List.of(secondArticle, firstArticle), articlesRepository.findPublished());
    }

}
