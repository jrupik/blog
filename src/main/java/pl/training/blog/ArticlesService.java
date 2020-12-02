package pl.training.blog;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ArticlesService {

    private final ArticlesRepository articlesRepository;

    public Article add(Article article) {
        return articlesRepository.save(article);
    }

    public void update(Article article) {
        articlesRepository.save(article);
    }

    public List<Article> getHistory(UUID articleUUID) {
        return articlesRepository.getHistoryByUUID(articleUUID);
    }

}
