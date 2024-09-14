package hello.netronics.repository;

import hello.netronics.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Like,Long> {

}
