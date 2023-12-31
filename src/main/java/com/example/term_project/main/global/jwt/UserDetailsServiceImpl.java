package com.example.term_project.main.global.jwt;

import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userInfo = userRepository.findByUserId(Long.parseLong(username));

        if (userInfo.isPresent()) {
            return User.withUsername(username)
                    .password(userInfo.get().getPassword())
                    .authorities(AuthorityUtils.NO_AUTHORITIES) // 권한 없음
                    .build();
        }
        else {
            throw new UsernameNotFoundException("User not found with userId: " + username);
        }
    }
}
