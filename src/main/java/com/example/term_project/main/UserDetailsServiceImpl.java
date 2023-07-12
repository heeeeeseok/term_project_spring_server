package com.example.term_project.main;

import com.example.term_project.main.user.data.UserEntity;
import com.example.term_project.main.user.data.UserRepository;
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
        Optional<UserEntity> userInfo = userRepository.findByUserName(username);

        if (userInfo.isPresent()) {
            return User.withUsername(username)
                    .password(userInfo.get().getPassword())
                    .authorities(AuthorityUtils.NO_AUTHORITIES)
                    .build();
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
