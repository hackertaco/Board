package com.nan.bungshin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.nan.bungshin.global.util.ApiResponseFactory.*;


@Service
@RequiredArgsConstructor
@Transactional
public class UserBusinessService {
    private final UserCRUDService userCRUDService;

    @Transactional(readOnly = true)
    public Map<String, Boolean> checkEmail(String email) {
        if(userCRUDService.loadWrappedUserFromEmail(email).isPresent()){
            return isAlreadyExistFlag();
        }
        return isNotAlreadyExistFlag();
    }

    public Map<String, Boolean> executeJoin(String password, String email) {
        User user = User.makeUser(password, email);
        userCRUDService.saveUser(user);
        return successFlag();
    }

    public Map<String, Boolean> executeLogin(String password, String email) {
        return successFlag();
    }
}
