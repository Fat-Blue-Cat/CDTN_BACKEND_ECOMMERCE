package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permissions;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermissionName(String permissionName);
}
