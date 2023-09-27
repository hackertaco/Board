package com.nan.bungshin.persistence;

import com.nan.bungshin.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPostIdAndId(Long postId, Long id);
    Page<Comment> findAllByPostId(Long postId, Pageable page);
}
