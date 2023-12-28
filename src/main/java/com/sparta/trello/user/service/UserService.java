package com.sparta.trello.user.service;

import com.sparta.trello.user.dto.*;
import com.sparta.trello.user.entity.User;
import com.sparta.trello.user.entity.UserRoleEnum;
import com.sparta.trello.user.jwt.JwtUtil;
import com.sparta.trello.user.repository.UserRepository;
import com.sparta.trello.user.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    @Transactional
    public void changeId(Long id, IdChangeDto idChangeDto, UserDetailsImpl userDetails) {
        User user =userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        if(userRepository.findByUsername(idChangeDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("본인만 수정 가능합니다");
        }
        if(idChangeDto.getUsername()==null){
            throw new IllegalArgumentException("아이디를 입력하셔야 합니다.");
        }
        user.changeIdUser(idChangeDto);

    }

    @Transactional
    public void changePassword(Long id, PasswordChangeDto passwordChangeDto, UserDetailsImpl userDetails) {
        User user =userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("본인만 수정 가능합니다");
        }
        if(passwordChangeDto.getBeforePassword()==null)throw new IllegalArgumentException("기존 비밀번호를 입력해야 변경 가능합니다.");
        if(passwordChangeDto.getAfterPassword()==null)throw new IllegalArgumentException("새로윤 비밀번호를 입력해야 변경 가능합니다.");

        if (!passwordEncoder.matches(passwordChangeDto.getBeforePassword(), userDetails.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String encodedPassword = passwordEncoder.encode(passwordChangeDto.getAfterPassword());
        user.changePasswordUser(encodedPassword);
    }

    public void deleteUser(Long id, UserDetailsImpl userDetails) {
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("본인만 수정 가능합니다");
        }

        userRepository.delete(user);
    }
}
