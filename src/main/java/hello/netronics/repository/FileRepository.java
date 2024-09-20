package hello.netronics.repository;

import hello.netronics.domain.File;
import hello.netronics.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByPostId(Long postId); // `Post` 엔티티의 기본 키가 `id`일 경우 유효

}
