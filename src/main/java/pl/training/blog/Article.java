package pl.training.blog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Article {

    @Getter
    private final Long id;
    @Setter
    private String contents;

}
