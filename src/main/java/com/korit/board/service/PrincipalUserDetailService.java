package com.korit.board.service;

import com.korit.board.security.PrincipalUser;
import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("UsernamNotFound");
        }

        return new PrincipalUser(user);
    }
}
