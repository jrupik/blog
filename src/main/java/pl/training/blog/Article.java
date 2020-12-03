package pl.training.blog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
import java.util.regex.Pattern;

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

    public long getWordsCount(String word) {
        return Pattern.compile(word, Pattern.CASE_INSENSITIVE)
                .matcher(contents)
                .results()
                .count();
    }

}
