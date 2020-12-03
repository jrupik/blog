package pl.training.blog.articles;

import lombok.*;
import pl.training.blog.commons.jpa.UUIDConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

import static pl.training.blog.articles.ArticleState.*;

@Entity
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Article {

    @GeneratedValue
    @Id
    @Getter
    private Long id;
    @Convert(converter = UUIDConverter.class)
    @Getter
    @Setter
    @NonNull
    private UUID uuid;
    @Getter
    @NonNull
    private String contents;
    @Getter
    @Setter
    private LocalDateTime publicationDate;
    @Enumerated(EnumType.STRING)
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
