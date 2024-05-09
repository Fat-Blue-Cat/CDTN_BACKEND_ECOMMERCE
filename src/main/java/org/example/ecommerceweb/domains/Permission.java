package org.example.ecommerceweb.domains;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "permissions")
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "create_permission")
    private Boolean createPermission;

    @Column(name = "read_permission")
    private Boolean readPermission;

    @Column(name = "update_permission")
    private Boolean updatePermission;

    @Column(name = "delete_permission")
    private Boolean deletePermission;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
