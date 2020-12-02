package pl.training.blog;

import java.util.List;
import java.util.UUID;

interface ArticlesRepository {

    Article save(Article article);

    List<Article> getHistoryByUUID(UUID articleUUID);

}
