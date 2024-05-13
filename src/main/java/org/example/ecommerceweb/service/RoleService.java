package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    void addRole(String roleName);
    void updateRole(Long roleId, String roleName);
    void deleteRole(Long roleId);
    Role getRole(Long roleId);
    void addPermissionToRole(Long roleId, List<Long> permissionIdList);
}
