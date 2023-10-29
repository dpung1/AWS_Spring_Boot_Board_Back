package com.korit.board.service;

import com.korit.board.dto.PointDisCountReqDto;
import com.korit.board.security.PrincipalUser;
import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailService implements UserDetailsService, OAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("UsernamNotFound");
        }

        return new PrincipalUser(user);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String provider = userRequest.getClientRegistration().getClientName();
        response.put("provider", provider);

        return new DefaultOAuth2User(new ArrayList<>(), response, "id");
    }

    public Boolean pointDisCount(PointDisCountReqDto pointDisCountReqDto) {
        return userMapper.pointDisCount(pointDisCountReqDto.toUserPointEntity()) > 0;
    }
}
