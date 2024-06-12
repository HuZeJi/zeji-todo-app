package com.huzeji.todomanager.svc.impl;

import com.huzeji.todomanager.repository.UserRepository;
import com.huzeji.todomanager.svc.UserDetailsSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSvcImpl implements UserDetailsSvc {
    @Autowired private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.find
    }
}
