package com.nan.bungshin.service;


import com.nan.bungshin.domain.Hashtag;
import com.nan.bungshin.persistence.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Optional<Hashtag> findByTagName(String tagName){
        return hashtagRepository.findByName(tagName);
    }
    public List<Hashtag> findByTagNameContains(String tagName){
        return hashtagRepository.findAllByNameContains(tagName);
    }

    public Hashtag save(String tagName){
        return hashtagRepository.save(Hashtag.builder().name(tagName).build());
    }
}
