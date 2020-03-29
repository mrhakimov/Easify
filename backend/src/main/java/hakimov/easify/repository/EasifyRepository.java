package hakimov.easify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hakimov.easify.domain.Post;

public interface EasifyRepository extends JpaRepository<Post, Long> {
}
