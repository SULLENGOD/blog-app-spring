package com.auth.auth_test.adapter.in;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth_test.application.dto.request.CreateUserDto;
import com.auth.auth_test.application.dto.request.LoginUserDto;
import com.auth.auth_test.application.dto.response.UserResponseDto;
import com.auth.auth_test.application.service.AuthenticationService;
import com.auth.auth_test.domain.model.User;
import com.auth.auth_test.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthtenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> creteUser(@RequestBody CreateUserDto newUser) {
        UserResponseDto registeredUser = authenticationService.signup(newUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> autheticate(@RequestBody LoginUserDto loginUserDto) {
        UserResponseDto authenticatedUser = authenticationService.authenticate(loginUserDto);
        User user = modelMapper.map(authenticatedUser, User.class);

        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(authenticatedUser);
    }
}
