package hello.netronics.dto;

import lombok.Data;

@Data
public class FileResponseDto {
    private Long fileId;
    private String filePath;
    private String fileName;
}
