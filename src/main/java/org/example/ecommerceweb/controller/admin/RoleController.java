package org.example.ecommerceweb.controller.admin;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRoles() {
        try {
            return ResponseEntity.ok(roleService.getAllRoles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRole(@RequestParam String roleName) {
        try {
            roleService.addRole(roleName);
            return ResponseEntity.ok("Role added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@RequestParam Long roleId, @RequestParam String roleName) {
        try {
            roleService.updateRole(roleId, roleName);
            return ResponseEntity.ok("Role updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.ok("Role deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{roleId}")
    public ResponseEntity<?> getRole(@PathVariable Long roleId) {
        try {
            return ResponseEntity.ok(roleService.getRole(roleId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/add-permission")
        public ResponseEntity<?> addPermissionToRole(@RequestParam Long roleId, @RequestParam List<Long> permissionIdList) {
        try {
            roleService.addPermissionToRole(roleId, permissionIdList);
            return ResponseEntity.ok("Permission added to role successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }






}
