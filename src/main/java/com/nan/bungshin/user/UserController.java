package com.nan.bungshin.user;

import com.nan.bungshin.global.ResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserBusinessService userBusinessService;
    @PostMapping("/loginEmail/check")
    public ResponseForm DuplicateEmail(@RequestBody UserRequestDto.CheckEmailForm checkEmailForm){
        return ResponseForm.success(userBusinessService.checkEmail(checkEmailForm.email()));
    }

    @PostMapping
    public ResponseForm join(@RequestBody UserRequestDto.JoinForm joinForm){
        return ResponseForm.success(userBusinessService.executeJoin(joinForm.password(), joinForm.email()));
    }

    @PostMapping("login")
    public ResponseForm login(@RequestBody UserRequestDto.LoginForm loginForm) {
        return ResponseForm.success(userBusinessService.executeLogin(loginForm.password(), loginForm.email()));
    }

}
