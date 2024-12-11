package com.example.userdomain.Security;

import com.example.userdomain.domain.user.UserRepository;
import com.example.userdomain.shared.exceptions.CustomUsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(userData -> {

                    userData.setUsername(userData.getEmail());
                    return new CustomUserDetail(userData);
                })
                .orElseThrow(() -> new CustomUsernameNotFoundException(username));

    }

}