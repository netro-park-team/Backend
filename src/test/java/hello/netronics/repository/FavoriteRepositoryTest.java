package hello.netronics.repository;

import hello.netronics.domain.Like;
import hello.netronics.domain.Post;
import hello.netronics.domain.Role;
import hello.netronics.domain.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class FavoriteRepositoryTest {

    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    private User user1;
    private User user2;
    private Post post1;
    private Post post2;

    @BeforeEach
    public void setUp() {
        user1 = new User("user1", "abc@abc.com", Role.USER);
        user2 = new User("user2", "def@def.com", Role.USER);

        post1 = new Post();
        post1.setTitle("DummyTitle");
        post1.setContent("DummyContent");
        post1.setUser(user1);

        post2 = new Post();
        post2.setTitle("DummyTitle2");
        post2.setContent("DummyContent2");
        post2.setUser(user1);
    }

    @Test
    public void saveAndGetLikeTest() {
        // given
        User user = this.user1;
        Post post = this.post1;

        userRepository.save(user);
        postRepository.save(post);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        favoriteRepository.save(like);

        // when
        Like findLike = favoriteRepository.findByUserIdAndPostId(user.getId(), post.getId())
                .orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(findLike.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(findLike.getPost().getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void getMultipleLikeTest() {
        // given
        User user = this.user1;
        Post post1 = this.post1;
        Post post2 = this.post2;

        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);

        Like like1 = new Like();
        Like like2 = new Like();

        like1.setUser(user);
        like1.setPost(post1);

        like2.setUser(user);
        like2.setPost(post2);

        favoriteRepository.save(like1);
        favoriteRepository.save(like2);

        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(IllegalArgumentException::new);

        Long user_id = findUser.getId();
        // when
        List<Like> findLikes = favoriteRepository.findAllByUserId(user_id);

        Like findLike1 = findLikes.get(0);
        Like findLike2 = findLikes.get(1);

        // then

        Assertions.assertThat(findLike1.getUser().getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(findLike2.getUser().getEmail()).isEqualTo(user.getEmail());

        Assertions.assertThat(findLike1.getPost().getTitle()).isEqualTo(post1.getTitle());
        Assertions.assertThat(findLike2.getPost().getTitle()).isEqualTo(post2.getTitle());
    }


    @Test
    public void deleteLikeTest() {
        // given
        Post post1 = this.post1;
        Post post2 = this.post2;
        User user = user1;

        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);

        Like like1 = new Like();
        like1.setUser(user);
        like1.setPost(post1);

        Like like2 = new Like();
        like2.setUser(user);
        like2.setPost(post2);

        favoriteRepository.save(like1);
        favoriteRepository.save(like2);

        // when
        favoriteRepository.deleteById(like1.getId());

        List<Like> likes = favoriteRepository.findAllByUserId(user.getId());

        // then
        assertThat(likes.size()).isEqualTo(1);
    }



}