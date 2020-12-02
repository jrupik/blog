package pl.training.blog;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ArticlesService {

    private final ArticlesRepository articlesRepository;

    public Article add(Article article) {
        articlesRepository.save(article);
        return null;
    }

    public void update(Article article) {
        articlesRepository.save(article);
    }

    public List<Article> getHistory(Long articleId) {
        return articlesRepository.getHistoryById(articleId);
    }

}
