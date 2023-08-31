package com.nan.bungshin.persistence;

import com.nan.bungshin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
