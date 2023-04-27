package com.example.Colorful_World.service;

import com.example.Colorful_World.entity.UserEntity;
import com.example.Colorful_World.repository.UserRepository;
import com.example.Colorful_World.token.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        return customUserDetails;
    }
}
