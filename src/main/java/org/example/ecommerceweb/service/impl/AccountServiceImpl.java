package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Role;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.RoleRepository;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.service.AccountService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public List<User> getAllAccounts() {
        return userRepository.findAll(Sort.by("id"));
    }

    @Override
    public void updateAccount(Long accountId, Long roleId) {
        User user = userRepository.findById(accountId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }
}
