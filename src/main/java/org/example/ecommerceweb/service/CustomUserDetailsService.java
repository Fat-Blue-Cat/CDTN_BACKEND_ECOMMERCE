package org.example.ecommerceweb.service;

import lombok.AllArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUserNameOrEmailAddress(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email")));
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
//        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.get().getRoleName()));
//
        if (user.get().getProviderId() != null) {
            return new org.springframework.security.core.userdetails.User(
                    usernameOrEmail,
                    "",
                    authorities
            );
        }
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.get().getPassword(),
                authorities

        );
    }
}
