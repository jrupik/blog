package pl.training.blog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

import static pl.training.blog.ArticleState.*;

@ToString
@RequiredArgsConstructor
public class Article {

    @Getter
    private final UUID uuid;
    @Getter
    private final String contents;

    @Getter
    @Setter
    private ArticleState state = DRAFT;

}
