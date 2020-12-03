package pl.training.blog.articles;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleTransferObject toArticleTransferObject(Article article);

    Article toArticle(ArticleTransferObject articleTransferObject);

    default String toUUIDString(UUID uuid) {
        return uuid.toString();
    }

    default UUID toUUID(String text) {
        return UUID.fromString(text);
    }

}
