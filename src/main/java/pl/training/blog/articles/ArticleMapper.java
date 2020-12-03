package pl.training.blog.articles;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleTransferObject toArticleTransferObject(Article article);

    Article toArticle(ArticleTransferObject articleTransferObject);

}
