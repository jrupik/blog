package pl.training.blog.articles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static pl.training.blog.commons.web.UriBuilder.requestUriWithId;

@RestController
@RequiredArgsConstructor
public class ArticlesRestController {

    private final ArticleMapper articleMapper;
    private final ArticlesService articlesService;

    @PostMapping(value = "/articles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleTransferObject> addArticle(@RequestBody ArticleTransferObject articleTransferObject) {
        var article = articlesService.add(articleMapper.toArticle(articleTransferObject));
        var locationUri = requestUriWithId(article.getId());
        return ResponseEntity.created(locationUri)
                .body(articleMapper.toArticleTransferObject(article));
    }

}
