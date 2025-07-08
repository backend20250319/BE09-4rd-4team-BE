package olive.oliveyoung.member.review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewUpdateDto {
    private Double rating;
    private String content;
}
