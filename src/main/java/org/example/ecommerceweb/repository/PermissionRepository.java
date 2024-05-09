package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permissions;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
