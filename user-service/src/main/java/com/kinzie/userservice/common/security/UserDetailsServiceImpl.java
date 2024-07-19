package com.kinzie.userservice.common.security;


import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + accountName));

        if (user == null) throw  new UsernameNotFoundException(accountName);

        return new UserDetailsImpl(user);
    }
}