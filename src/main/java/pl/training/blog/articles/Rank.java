package pl.training.blog.articles;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rank implements Comparable<Rank> {

    private Long value;
    private Article article;

    @Override
    public int compareTo(Rank otherRank) {
        return otherRank.value.compareTo(value);
    }

}
