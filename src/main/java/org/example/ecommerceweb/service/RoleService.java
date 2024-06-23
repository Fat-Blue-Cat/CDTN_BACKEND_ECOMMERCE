package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role addRole(String roleName, List<Long> permissionIdList);
    Role updateRole(Long roleId, String roleName, List<Long> permissionIdList);
    void deleteRole(Long roleId);
    Role getRole(Long roleId);
    void addPermissionToRole(Long roleId, List<Long> permissionIdList);
}
