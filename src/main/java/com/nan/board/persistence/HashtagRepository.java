package com.nan.board.persistence;

import com.nan.board.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String tagName);
    List<Hashtag> findAllByNameContains(String tagName);
}
