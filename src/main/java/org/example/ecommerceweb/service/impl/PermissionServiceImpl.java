package org.example.ecommerceweb.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.commons.Constant;
import org.example.ecommerceweb.domains.Permission;
import org.example.ecommerceweb.domains.Role;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.PermissionRepository;
import org.example.ecommerceweb.repository.RoleRepository;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.service.PermissionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    @Transactional
    public void init() {

        if(roleRepository.findAll().isEmpty()){
            List<Role> roles = List.of(
                    Role.builder().roleName(Constant.ROLE_ADMIN).build(),
                    Role.builder().roleName(Constant.ROLE_USER).build()
            );

            roleRepository.saveAll(roles);
        }
        if(permissionRepository.findAll().isEmpty()){
            List<Permission> permissions = List.of(
                    Permission.builder().permissionName(Constant.PERMISSION_ADMIN).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_USER).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_ROLE).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_CATEGORY).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_PRODUCT).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_ORDER).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_COUPON).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_BRAND).build(),
                    Permission.builder().permissionName(Constant.PERMISSION_MANAGER_REVENUE).build()

            );
            permissionRepository.saveAll(permissions);

            Permission permission = permissionRepository.findByPermissionName(Constant.PERMISSION_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Permission not found"));
            permission.setRoles(new HashSet<>());
            Role role = roleRepository.findByRoleName(Constant.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            role.setPermissions(new HashSet<>());


            role.getPermissions().add(permission);
            roleRepository.save(role);

        }

        if (userRepository.findAll().isEmpty()) {
            Role role = roleRepository.findByRoleName(Constant.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            User user = User.builder().userName("admin").password(passwordEncoder.encode("admin"))
                    .emailAddress("admin@gmail.com").role(role).build();
            userRepository.save(user);
        }

    }


//    @Override
//    public void addPermission(Permission permission) {
//
//        permissionRepository.save(permission);
//    }
//
//    @Transactional
//    @Override
//    public void deletePermission(Long permissionId) {
//        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new RuntimeException("Permission not found"));
//        if(permission.getPermissionName().equals(Constant.PERMISSION_ADMIN) || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_USER) || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_ROLE)
//        || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_CATEGORY) || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_PRODUCT) || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_ORDER)
//        || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_COUPON) || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_BRAND) || permission.getPermissionName().equals(Constant.PERMISSION_MANAGER_REVENUE)
//        ){
//            throw new RuntimeException("Permission can not be deleted");
//        }
//        for (Role role : permission.getRoles()) {
//            role.getPermissions().remove(permission);
//            roleRepository.save(role);
//        }
//
//        permissionRepository.deleteById(permissionId);
//    }
//
//    @Transactional
//    @Override
//    public void updatePermission(Long permissionId, Permission permission){
//        Permission permissionCurrent = permissionRepository.findById(permissionId).orElseThrow(() -> new RuntimeException("Permission not found"));
//        permissionCurrent.setPermissionName(permission.getPermissionName());
//        permissionCurrent.setAlternativeName(permission.getAlternativeName());
//        permissionCurrent.setCreatePermission(permission.getCreatePermission());
//        permissionCurrent.setReadPermission(permission.getReadPermission());
//        permissionCurrent.setUpdatePermission(permission.getUpdatePermission());
//        permissionCurrent.setDeletePermission(permission.getDeletePermission());
//        permissionRepository.save(permissionCurrent);
//
//    }



    @Override
    public Permission getPermission(Long permissionId) {
        return permissionRepository.findById(permissionId).orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
}
