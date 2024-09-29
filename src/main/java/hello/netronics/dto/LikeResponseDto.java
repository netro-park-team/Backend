package hello.netronics.dto;

import lombok.Data;

@Data
public class LikeResponseDto {
    private Long likeId;
    private Long postId;
    private String username;  // 좋아요를 누른 사용자 정보
}
