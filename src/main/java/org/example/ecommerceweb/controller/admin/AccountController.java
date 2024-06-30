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

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestParam String userName) {
        try {

            return ResponseEntity.ok(accountService.createAccount(userName));
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

    @PutMapping("/change-status")
    public ResponseEntity<?> changeStatusAccount(@RequestParam Long userId) {
        try {

            return ResponseEntity.ok(accountService.changeStatusAccount(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(accountService.resetPassword(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long userId) {
        try {
            accountService.deleteAccount(userId);
            return ResponseEntity.ok("Account deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
