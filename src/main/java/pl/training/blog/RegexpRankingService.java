package pl.training.blog;

import java.util.regex.Pattern;

public class RegexpRankingService implements RankingService {

    @Override
    public Rank getRank(String keyword, Article article) {
        return new Rank(Pattern.compile(keyword, Pattern.CASE_INSENSITIVE)
                .matcher(article.getContents())
                .results()
                .count(), article);
    }

}
