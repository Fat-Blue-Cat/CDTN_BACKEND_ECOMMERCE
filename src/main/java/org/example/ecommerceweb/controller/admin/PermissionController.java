package org.example.ecommerceweb.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/permission")
public class PermissionController {
    private final org.example.ecommerceweb.service.PermissionService permissionService;

//    @PostMapping("/add")
//    public ResponseEntity<?> addPermission(@RequestBody Permission permission) {
//        try {
//            permissionService.addPermission(permission);
//            return ResponseEntity.ok("Permission added successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }


//    @DeleteMapping("/delete/{permissionId}")
//    public ResponseEntity<?> deletePermission(@PathVariable Long permissionId) {
//        try {
//            permissionService.deletePermission(permissionId);
//            return ResponseEntity.ok("Permission deleted successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPermissions() {
        try {
            return ResponseEntity.ok(permissionService.getAllPermissions());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//
//    @PutMapping("/update/{permissionId}")
//    public ResponseEntity<?> updatePermission( @PathVariable Long permissionId, @RequestBody Permission permission) {
//        try {
//            permissionService.updatePermission(permissionId, permission);
//            return ResponseEntity.ok("Permission updated successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }



    @GetMapping("/get/{permissionId}")
    public ResponseEntity<?> getPermission(@PathVariable Long permissionId) {
        try {
            return ResponseEntity.ok(permissionService.getPermission(permissionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
