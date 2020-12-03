package pl.training.blog.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.training.blog.BlogApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.file.Files.readString;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.training.blog.articles.TestArticlesProvider.getPublishedArticle;

@Tag("medium")
@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ArticlesIntegrationTest {

    private final Article firstArticle = getPublishedArticle("java", LocalDateTime.now());
    private final Article secondArticle = getPublishedArticle("java java", LocalDateTime.now().plusDays(1));
    private final ArticleTransferObject articleTransferObject = new ArticleTransferObject();

    @Autowired
    private ArticlesService articlesService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        articleTransferObject.setUuid("fd687ca4-07fe-41bc-b0c3-c5f0445394ab");
        articlesService.add(firstArticle);
        articlesService.add(secondArticle);
    }

    @Test
    void articles_searched_by_the_keyword_are_sorted_by_rank_in_descending_order() {
        var result = articlesService.findArticlesByKeyword("java").stream()
                .map(Article::getUuid)
                .collect(toList());
        assertEquals(List.of(secondArticle.getUuid(), firstArticle.getUuid()), result);
    }

    @Test
    void should_add_article() throws Exception {
        var body = mockMvc.perform(post("/articles")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleTransferObject)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(LOCATION))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(readString(Path.of("added-article.json")), body);
    }

}
