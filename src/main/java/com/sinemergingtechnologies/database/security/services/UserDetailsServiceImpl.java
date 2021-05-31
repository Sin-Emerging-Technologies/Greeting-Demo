package com.sinemergingtechnologies.database.security.services;
import com.sinemergingtechnologies.database.model.User;
import com.sinemergingtechnologies.database.repository.UserRepository;
import com.sinemergingtechnologies.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

//        if (user.isPresent()) {
            return UserDetailsImpl.build(user.get());
//        }

//        return UserDetailsImpl
    }

}
