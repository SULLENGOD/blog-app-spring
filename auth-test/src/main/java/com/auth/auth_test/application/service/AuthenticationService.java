package com.auth.auth_test.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.auth_test.adapter.out.UserRepository;
import com.auth.auth_test.application.dto.request.CreateUserDto;
import com.auth.auth_test.application.dto.request.LoginUserDto;
import com.auth.auth_test.application.dto.response.UserResponseDto;
import com.auth.auth_test.domain.model.User;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDto signup(CreateUserDto newUser) {
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setUsername(newUser.getUsername());

        user = userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()));

        User user = userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow();

        return modelMapper.map(user, UserResponseDto.class);
    }
}
