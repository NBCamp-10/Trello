package com.sparta.trello.user.security;

import com.sparta.trello.user.entity.User;
import com.sparta.trello.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        return new UserDetailsImpl(user);
    }

}
