package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wp.domain.Post;

public interface EasifyRepository extends JpaRepository<Post, Long> {
}
