package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.User;

import java.util.List;

public interface AccountService {

    List<User> getAllAccounts();
    void updateAccount(Long accountId, Long roleId);
    User createAccount(String userName);
    User changeStatusAccount(Long accountId);
}
