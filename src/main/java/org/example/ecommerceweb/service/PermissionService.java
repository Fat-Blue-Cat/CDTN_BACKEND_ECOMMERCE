package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Permission;

import java.util.List;

public interface PermissionService{
//    void addPermission(Permission permission);
//    void deletePermission(Long permissionId);
//    void updatePermission(Long permissionId,Permission permission);
    Permission getPermission(Long permissionId);
    List<Permission> getAllPermissions();

}
