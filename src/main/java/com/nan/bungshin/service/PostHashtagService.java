package com.nan.bungshin.service;

import com.nan.bungshin.domain.Hashtag;
import com.nan.bungshin.domain.Post;
import com.nan.bungshin.domain.PostHashtag;
import com.nan.bungshin.persistence.PostHashtagRepository;
import com.nan.bungshin.persistence.PostRepository;
import com.nan.bungshin.service.dto.FindByHashTagDTO;
import com.nan.bungshin.service.dto.PostHashtagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Transactional
public class PostHashtagService {
    private final HashtagService hashtagService;
    private final PostRepository postRepository;
    private final PostHashtagRepository postHashtagRepository;
//    private final PostHashtagService postHashtagService;

    public void saveHashtag(Post post, List<String> hashtags){
        if(hashtags.size() == 0) return;

        hashtags.stream()
                .map(hashtag -> hashtagService.findByTagName(hashtag)
                                .orElseGet(() -> hashtagService.save(hashtag)))
                .forEach(hashtag -> mapHashtagToPost(post, hashtag));
    }
    private Long mapHashtagToPost(Post post, Hashtag hashtag){
        return postHashtagRepository.save(new PostHashtag(post, hashtag)).getId();
    }
    public List<PostHashtag> findHashtagListByPost(Post post) {
        return postHashtagRepository.findAllByPost(post);
    }
    public List<PostHashtagDto.Response> getAllHashtags(){
        List<PostHashtag> hashtags = postHashtagRepository.findAll();
        List<PostHashtagDto.Response> list = new ArrayList<>();
        for(PostHashtag h: hashtags){
            list.add(new PostHashtagDto.Response(h));
        }
        return list;
    }

    public List<FindByHashTagDTO> getPostByHashtag(String hashtag){
        List<FindByHashTagDTO> posts = new ArrayList<>();
        List<FindByHashTagDTO> result = postHashtagRepository.findByHashtag(hashtag).stream().map(h -> new FindByHashTagDTO(h)).collect(Collectors.toList());
        Map<Long, List<FindByHashTagDTO>> PostByID = result.stream().collect(groupingBy(FindByHashTagDTO::getId));
        for (long k : PostByID.keySet()) {
            List<String> hids = new ArrayList<>();
            List<FindByHashTagDTO> fbs = PostByID.get(k);

            for (FindByHashTagDTO f: fbs ){
                if (hids.stream().filter(x -> x == f.getName()).collect(Collectors.toList()).size() > 0 ) {
                    continue;
                }

                hids.add(f.getName());
                f.setHashtags(hids);
            }
            posts.add(fbs.get(0));
        }


        return posts;
    }
}
