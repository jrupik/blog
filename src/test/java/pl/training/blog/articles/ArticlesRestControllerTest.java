package pl.training.blog.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.training.blog.articles.TestArticlesProvider.getPublishedArticle;

@WebMvcTest(ArticlesRestController.class)
@ExtendWith(SpringExtension.class)
public class ArticlesRestControllerTest {

    @MockBean
    private ArticlesService articlesService;
    @MockBean
    private ArticleMapper articleMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final ArticleTransferObject articleTransferObject = new ArticleTransferObject();

    @BeforeEach
    void setup() {
        articleTransferObject.setUuid(UUID.randomUUID().toString());
        var article = getPublishedArticle("Java", LocalDateTime.now());
        when(articleMapper.toArticle(articleTransferObject)).thenReturn(article);
        when(articlesService.add(any(Article.class))).then(returnsFirstArg());
        when(articleMapper.toArticleTransferObject(article)).thenReturn(articleTransferObject);
    }

    @Test
    void should_add_article() throws Exception {
        mockMvc.perform(post("/articles")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleTransferObject)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(LOCATION))
                .andExpect(jsonPath("$.uuid", is(articleTransferObject.getUuid())));
    }

}
