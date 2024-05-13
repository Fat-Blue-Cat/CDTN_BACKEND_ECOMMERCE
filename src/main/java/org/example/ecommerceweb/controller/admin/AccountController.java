package org.example.ecommerceweb.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/account")
public class AccountController {
    private final org.example.ecommerceweb.service.AccountService accountService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAccounts() {
        try {
            return ResponseEntity.ok(accountService.getAllAccounts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRoleforUser(@RequestParam Long userId, @RequestParam Long roleId) {
        try {
            accountService.updateAccount(userId, roleId);
            return ResponseEntity.ok("Account updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}