package com.nan.bungshin.controller;

import com.nan.bungshin.service.UserService;
import com.nan.bungshin.service.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserRestController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/assert/join")
    public ResponseEntity<String> check(@Valid UserDto.Request dto){
        userService.checkEmailDuplicate(dto);
        userService.checkNicknameDuplicate(dto);
        userService.checkUsernameDuplicate(dto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping("/user")
    public ResponseEntity<String> modify(@RequestBody UserDto.Request dto) {
        userService.modify(dto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
