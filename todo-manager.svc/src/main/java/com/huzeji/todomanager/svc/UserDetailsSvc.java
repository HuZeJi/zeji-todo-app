package com.huzeji.todomanager.svc;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsSvc {
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException;
}
