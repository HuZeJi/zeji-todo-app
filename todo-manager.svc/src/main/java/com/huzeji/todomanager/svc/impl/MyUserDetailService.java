package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.UserEntity;
import com.huzeji.todomanager.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserSvc userSvc;
    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Map<String, Object> usernameFilter = new HashMap<>();
        usernameFilter.put( "nickname", username );
        UserEntity user = userSvc.get( usernameFilter )
                .stream()
                .findFirst()
                .orElseThrow( () -> new UsernameNotFoundException("User not found") );
        return new User(user.getNickname(), new BCryptPasswordEncoder().encode(user.getPassword()), new ArrayList<>());
    }
}
