package pl.training.blog.articles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ArticlesRepository extends Repository<Article, Long> {

    Article save(Article article);

    List<Article> findAll();

    @Query("select a from Article a where a.state = 'PUBLISHED' order by a.publicationDate desc")
    List<Article> findPublished();

}
