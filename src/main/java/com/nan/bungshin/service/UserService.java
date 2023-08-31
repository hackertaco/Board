package com.nan.bungshin.service;

import com.nan.bungshin.domain.User;
import com.nan.bungshin.persistence.UserRepository;
import com.nan.bungshin.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Transactional
    public void checkUsernameDuplicate(UserDto.Request dto) {
        boolean usernameDuplicate = userRepository.existsByUsername(dto.toEntity().getUsername());
        if(usernameDuplicate){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }
    @Transactional
    public void checkEmailDuplicate(UserDto.Request dto) {
        boolean emailDuplicate = userRepository.existsByEmail(dto.toEntity().getEmail());
        if(emailDuplicate){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
    @Transactional
    public void checkNicknameDuplicate(UserDto.Request dto) {
        boolean nicknameDuplicate = userRepository.existsByNickname(dto.toEntity().getNickname());
        if(nicknameDuplicate){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
    @Transactional
    public Long userJoin(UserDto.Request dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        return userRepository.save(dto.toEntity()).getId();
    }
    @Transactional
    public void modify(UserDto.Request dto) {
        User user = userRepository.findById(dto.toEntity().getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String encPassword = encoder.encode(dto.getPassword());
        user.modify(encPassword);
    }
}
