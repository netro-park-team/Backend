package hello.netronics.dto;

import lombok.Data;

@Data
public class FileRequestDto {
    private Long postId;
    private String filePath;
    private String fileName;
}
