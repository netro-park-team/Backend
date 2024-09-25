package hello.netronics.service;

import hello.netronics.domain.Like;
import hello.netronics.domain.Post;
import hello.netronics.domain.Role;
import hello.netronics.domain.User;
import hello.netronics.dto.LikeResponseDto;
import hello.netronics.repository.FavoriteRepository;
import hello.netronics.repository.PostRepository;
import hello.netronics.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LikeServiceTest {

    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeService likeService;

    private User user1;
    private User sUser;
    private Post post1;
    private Post post2;

    @BeforeEach
    public void setUp() {
        sUser = new User("superUser", "su@su.com", Role.USER);
        userRepository.save(sUser);
        user1 = new User("user1", "abc@abc.com", Role.USER);

        post1 = new Post();
        post1.setTitle("DummyTitle");
        post1.setContent("DummyContent");
        post1.setUser(sUser);

        post2 = new Post();
        post2.setTitle("DummyTitle2");
        post2.setContent("DummyContent2");
        post2.setUser(sUser);
    }

    @Test
    @DisplayName("좋아요 테스트")
    public void addLikeTest() {
        // given
        User user = user1;
        Post post = post1;

        userRepository.save(user);
        postRepository.save(post);

        // when
        likeService.addLike(user.getId(), post.getId());

        List<Like> likes = favoriteRepository.findAllByUserId(user.getId());

        Like findLike = likes.get(0);

        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(IllegalArgumentException::new);

        Post findPost = postRepository.findById(post.getId())
                .orElse(new Post());

        // then
        assertThat(findLike.getUser().getEmail()).isEqualTo(findUser.getEmail());
        assertThat(findLike.getPost().getTitle()).isEqualTo(findPost.getTitle());

        assertThat(findUser.getLikes().size()).isEqualTo(1);
        assertThat(findPost.getLikes().size()).isEqualTo(1);

        assertThat(findUser.getLikes().get(0).getUser().getEmail()).isEqualTo(findUser.getEmail());
    }

    @Test
    public void deleteLikeTest() {
        // given
        User user = user1;
        Post post = post1;

        userRepository.save(user);
        postRepository.save(post);

        likeService.addLike(user.getId(), post.getId());

        // when
        likeService.deleteLike(user.getId(), post.getId());

        Optional<Like> likeOpt = likeService.loadLikeByUserIdAndPostId(user.getId(), post.getId());

        User findUser = userRepository.findById(user.getId())
                .orElse(new User());
        Post findPost = postRepository.findById(post.getId())
                .orElse(new Post());

        // then
        assertThat(likeOpt.isEmpty()).isEqualTo(true);

        assertThat(findUser.getLikes().size()).isEqualTo(0);
        assertThat(findPost.getLikes().size()).isEqualTo(0);
    }

    @Test
    public void getAllLikesTest() {
        // given
        User user = user1;
        Post post1 = this.post1;
        Post post2 = this.post2;

        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);

        // when
        likeService.addLike(user.getId(), post1.getId());
        likeService.addLike(user.getId(), post2.getId());

        List<LikeResponseDto> likes = likeService.loadPostAllByUserId(user.getId());
        // then


        assertThat(likes.size()).isEqualTo(2);

        assertThat(likes.get(0).getPostId()).isEqualTo(post1.getId());
        assertThat(likes.get(1).getPostId()).isEqualTo(post2.getId());

        assertThat(likes.get(0).getUsername()).isEqualTo(user.getName());
        assertThat(likes.get(1).getUsername()).isEqualTo(user.getName());
    }
}