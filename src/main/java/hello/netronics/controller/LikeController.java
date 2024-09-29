package hello.netronics.controller;

import hello.netronics.auth.LoginUser;
import hello.netronics.domain.User;
import hello.netronics.dto.LikeResponseDto;
import hello.netronics.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/")
    public ResponseEntity<List<LikeResponseDto>> likeList(@LoginUser User loginUser) {
        List<LikeResponseDto> posts = likeService.loadLikeAllByUserId(loginUser.getId());

        return ResponseEntity.status(200).body(posts);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<LikeResponseDto> addLike(@PathVariable("postId") Long postId, @LoginUser User loginUser) {
        LikeResponseDto dto = likeService.addLike(loginUser.getId(), postId);
        return ResponseEntity.status(200).body(dto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteLike(@PathVariable("postId") Long postId, @LoginUser User loginUser) {
        likeService.deleteLike(loginUser.getId(), postId);
        return ResponseEntity.status(200).build();
    }
    
}
