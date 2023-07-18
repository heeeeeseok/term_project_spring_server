package com.example.term_project.main.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByPostId(Long postId);
}
