package pl.training.blog;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ArticlesService {

    private final ArticlesRepository articlesRepository;

    public Article add(Article article) {
        article.setState(ArticleState.DRAFT);
        return articlesRepository.save(article);
    }

    public void update(Article article) {
        add(article);
    }

    public void publish(Article article) {
        article.setState(ArticleState.PUBLISHED);
        articlesRepository.save(article);
    }

    public List<Article> findArticlesByKeyword(String keyword) {
        return articlesRepository.findAll().stream()
                .map(article -> new Rank(article.getWordsCount(keyword), article))
                .sorted()
                .map(Rank::getArticle)
                .collect(toList());
    }

}
