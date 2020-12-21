package com.fof.init.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDetailsService {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
