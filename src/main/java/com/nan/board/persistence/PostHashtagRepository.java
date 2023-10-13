package com.nan.board.persistence;

import com.nan.board.domain.Post;
import com.nan.board.domain.PostHashtag;
import com.nan.board.service.dto.IfindByHashtagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
    List<PostHashtag> findAllByPost(Post post);
    //select p.hashtag, t from PostHashtag p Left join p.post t on p.post.id = t.id
    @Query(value = "SELECT p.id as id, p.author as author, p.title as title, p.view as view, p.content as content, h2.name as name\n" +
            "FROM post_hashtag ph\n" +
            "Inner JOIN posts p on ph.post_id = p.id\n" +
            "Inner JOIN hashtags h2 on ph.hashtag_id = h2.id\n" +
            "WHERE ph.hashtag_id IN (SELECT h.id FROM hashtags h WHERE h.name like %:hashtag%)\n", nativeQuery = true)
    List<IfindByHashtagDTO> findByHashtag(@Param("hashtag") String hashtag);
}
