package pl.training.blog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticlesService {

    private final ArticlesRepository articlesRepository;

    public Article add(Article article) {
        articlesRepository.save(article);
        return null;
    }

}
