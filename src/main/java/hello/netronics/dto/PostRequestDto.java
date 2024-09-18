package hello.netronics.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {
    private String title;
    private String content;
    private List<String> filePaths;  // 업로드할 파일 경로 리스트
}
