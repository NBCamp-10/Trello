package com.sparta.trello.user.controller;


import com.sparta.trello.user.dto.*;
import com.sparta.trello.user.security.UserDetailsImpl;
import com.sparta.trello.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody UserRequestDto requestDto ) {
        try {
            userService.signup(requestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("회원가입 되셨습니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        try{
            userService.login(requestDto,response);
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공",HttpStatus.OK.value()));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
           return ResponseEntity.ok().body(userService.getUser(userDetails));
        }catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<CommonResponseDto> changeId(@PathVariable Long id, @RequestBody IdChangeDto idChangeDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            userService.changeId(id,idChangeDto,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("아이디가 변경 되었습니다.",HttpStatus.OK.value()));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<CommonResponseDto> changePassword(@PathVariable Long id,@RequestBody PasswordChangeDto passwordChangeDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            userService.changePassword(id,passwordChangeDto,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("비밀번호가 변경 되었습니다.",HttpStatus.OK.value()));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponseDto> deleteUser(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            userService.deleteUser(id,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("사용자가 삭제 되었습니다..",HttpStatus.OK.value()));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
}