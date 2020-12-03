package pl.training.blog.articles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticlesRestController.class)
@ExtendWith(SpringExtension.class)
public class ArticlesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArticlesService articlesService;

    @Test
    void should_add_article() throws Exception {
        var articleTransferObject = new ArticleTransferObject();
        mockMvc.perform(post("/articles", articleTransferObject).accept(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().doesNotExist(HttpHeaders.LOCATION))
            .andExpect(jsonPath("$.uuid", notNullValue()));
    }

}
