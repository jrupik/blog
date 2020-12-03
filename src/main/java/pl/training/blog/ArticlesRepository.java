package pl.training.blog;

import org.springframework.data.repository.Repository;

import java.util.List;

interface ArticlesRepository extends Repository<Article, Long> {

    Article save(Article article);

    List<Article> findAll();

    List<Article> findPublished();

}
