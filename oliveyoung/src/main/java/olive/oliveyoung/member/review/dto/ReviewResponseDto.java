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
    private String userName; // userId -> userName
    private Long productId;
    private Double rating;
    private String skinType;
    private String skinConcern;
    private String texture;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
