package com.sparta.trello.user.service;

import com.sparta.trello.user.dto.CommonResponseDto;
import com.sparta.trello.user.dto.LoginRequestDto;
import com.sparta.trello.user.dto.UserRequestDto;
import com.sparta.trello.user.dto.UserResponseDto;
import com.sparta.trello.user.entity.User;
import com.sparta.trello.user.entity.UserRoleEnum;
import com.sparta.trello.user.jwt.JwtUtil;
import com.sparta.trello.user.repository.UserRepository;
import com.sparta.trello.user.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //관리자 키
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public void signup(UserRequestDto requestDto) {
        if(userRepository.findByUsername(requestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }
        UserRoleEnum role =UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 인증 번호가 다릅니다.");
            }
            role=UserRoleEnum.ADMIN;
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto,encodedPassword,role);
        userRepository.save(user);

    }

    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username =requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당이름이 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDto.getUsername(),user.getRole()));
    }

    public UserResponseDto getUser(UserDetailsImpl userDetails) {
        User user=userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new IllegalArgumentException("없는 유저입니다."));
        return new UserResponseDto(user);

    }
}
