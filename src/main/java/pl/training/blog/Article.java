package pl.training.blog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class Article {

    @Getter
    private final UUID uuid;

}
