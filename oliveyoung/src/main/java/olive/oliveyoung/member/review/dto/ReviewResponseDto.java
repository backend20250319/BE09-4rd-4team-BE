package olive.oliveyoung.member.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private Long productId;
    private Double rating;
    private Integer userReviewNumber;
    private String content;
    private LocalDateTime createdAt;
}
