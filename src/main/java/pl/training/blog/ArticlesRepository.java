package pl.training.blog;

import java.util.List;

interface ArticlesRepository {

    Article save(Article article);

    List<Article> findAll();

}
