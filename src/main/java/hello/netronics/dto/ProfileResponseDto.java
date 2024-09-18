package hello.netronics.dto;

import lombok.Data;

@Data
public class ProfileResponseDto {
    private Long profileId;
    private String profileImage;
    private String bio;
}
