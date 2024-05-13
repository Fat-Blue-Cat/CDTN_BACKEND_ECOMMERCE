package org.example.ecommerceweb.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.commons.Constant;
import org.example.ecommerceweb.domains.Permission;
import org.example.ecommerceweb.domains.Role;
import org.example.ecommerceweb.repository.PermissionRepository;
import org.example.ecommerceweb.repository.RoleRepository;
import org.example.ecommerceweb.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;




    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void addRole(String roleName) {
        Role role = Role.builder().roleName(roleName).build();
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Long roleId, String roleName) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        role.setRoleName(roleName);
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        if(role.getRoleName().equals(Constant.ROLE_ADMIN)){
            throw new RuntimeException("Admin role can not be deleted");
        }
        if(role.getRoleName().equals(Constant.ROLE_USER)){
            throw new RuntimeException("User role can not be deleted");
        }
        if(!role.getUsers().isEmpty()){
            throw new RuntimeException("Role is assigned to a user and cannot be deleted");
        }

        for (Permission permission : role.getPermissions()) {
            permission.getRoles().remove(role);
            permissionRepository.save(permission);
        }

        roleRepository.deleteById(roleId);
    }

    @Override
    public Role getRole(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Transactional
    @Override
    public void addPermissionToRole(Long roleId, List<Long> permissionIdList) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        role.getPermissions().clear();
        roleRepository.save(role);
        List<Permission> permissions = permissionRepository.findAllById(permissionIdList);
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
    }
}
