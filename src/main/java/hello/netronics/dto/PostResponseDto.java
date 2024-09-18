package hello.netronics.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String author;  // 작성자 정보
    private List<FileResponseDto> files;
    private List<CommentResponseDto> comments;
    private List<LikeResponseDto> likes;
}
