package com.nan.bungshin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class UserCRUDService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> loadWrappedUserFromEmail(String email) { return userRepository.findByEmail(email);}
}
