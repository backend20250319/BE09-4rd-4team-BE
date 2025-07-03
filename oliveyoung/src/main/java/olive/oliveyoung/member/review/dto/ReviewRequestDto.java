package olive.oliveyoung.member.review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewRequestDto {
    private Long userId;
    private Long productId;
    private Double rating;
    private String content;
}
