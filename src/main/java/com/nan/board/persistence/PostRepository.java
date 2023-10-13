package com.nan.board.persistence;

import com.nan.board.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    int updateView(Long id);
    @Query("select p from Post p")
    List<Post> findWithPagination(Pageable pageOption);

    List<Post> findByTitleContains(String title);
}
