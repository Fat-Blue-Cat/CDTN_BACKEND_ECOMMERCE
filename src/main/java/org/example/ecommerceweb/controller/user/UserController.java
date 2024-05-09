package org.example.ecommerceweb.controller.user;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.security.CurrentUser;
import org.example.ecommerceweb.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
//    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findByUserNameOrEmailAddress(userPrincipal.getEmail(), userPrincipal.getName()).get();
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}