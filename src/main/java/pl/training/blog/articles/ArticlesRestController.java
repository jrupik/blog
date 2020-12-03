package pl.training.blog.articles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticlesRestController {

    @PostMapping("/articles")
    public ResponseEntity<ArticleTransferObject> addArticle(@RequestBody ArticleTransferObject articleTransferObject) {
        return ResponseEntity.ok().build();
    }

}
