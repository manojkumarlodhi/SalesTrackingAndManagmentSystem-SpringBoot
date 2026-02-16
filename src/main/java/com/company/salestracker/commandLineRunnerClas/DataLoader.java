//package com.company.salestracker.commandLineRunnerClas;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.company.salestracker.entity.Permission;
//import com.company.salestracker.entity.Role;
//import com.company.salestracker.entity.Status;
//import com.company.salestracker.entity.User;
//import com.company.salestracker.repository.PermissionRepository;
//import com.company.salestracker.repository.RoleRepository;
//import com.company.salestracker.repository.UserRepository;
//import com.company.salestracker.util.PermissionCodes;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final PermissionRepository permissionRepository;
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataLoader(
//            PermissionRepository permissionRepository,
//            RoleRepository roleRepository,
//            UserRepository userRepository,
//            PasswordEncoder passwordEncoder) {
//
//        this.permissionRepository = permissionRepository;
//        this.roleRepository = roleRepository;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    // ======================================================
//    // RUN
//    // ======================================================
//    @Override
//    public void run(String... args) {
//
//        System.out.println("========== DATA LOADER START ==========");
//
//        createAllPermissions();
//
//        Role superAdmin = createSuperAdminRole();
//       
//
//        createSuperAdminUser(superAdmin);
//
//
//        System.out.println("========== DATA LOADER END ==========");
//    }
//
//    // ======================================================
//    // PERMISSIONS
//    // ======================================================
//    private void createAllPermissions() {
//
//        List<String> permissions = List.of(
//                PermissionCodes.READ_ONLY,
//                PermissionCodes.ALL_VIEW,
//                PermissionCodes.CREATE_ADMIN,
//                PermissionCodes.CREATE_SUPER_ADMIN,
//                PermissionCodes.CREATE_MANAGER,
//                PermissionCodes.DELETE,
//                PermissionCodes.CREATE_SALES,
//                PermissionCodes.UPDATE,
//                PermissionCodes.MANAGE_USERS,
//                PermissionCodes.VIEW_REPORTS,
//                PermissionCodes.ASSIGN_TARGETS,
//                PermissionCodes.VIEW_TEAM,
//                PermissionCodes.CREATE_LEAD,
//                PermissionCodes.UPDATE_LEAD,
//                PermissionCodes.CREATE_DEAL,
//                PermissionCodes.RECORD_SALE
//        );
//        
//        for (String code : permissions) {
//
//            permissionRepository.findByPermissionCode(code)
//                    .orElseGet(() -> {
//                        Permission p = new Permission();
//                        p.setPermissionCode(code);
//                        p.setDescription(code.replace("_", " "));
//                        System.out.println("Permission created: " + code);
//                        return permissionRepository.save(p);
//                    });
//        }
//    }
//
//    // ======================================================
//    // ROLES
//    // ======================================================
//    private Role createSuperAdminRole() {
//
//        return roleRepository.findByRoleName("SUPER_ADMIN")
//                .orElseGet(() -> {
//
//                    Role role = new Role();
//                    role.setRoleName("SUPER_ADMIN");
//                    role.setDescription("Super Admin with all permissions");
//
//                    role.setPermissions(getPermissions(Set.of(
//                            PermissionCodes.READ_ONLY,
//                            PermissionCodes.ALL_VIEW,
//                            PermissionCodes.CREATE_ADMIN,
//                            PermissionCodes.CREATE_SUPER_ADMIN,
//                            PermissionCodes.CREATE_MANAGER,
//                            PermissionCodes.DELETE,
//                            PermissionCodes.CREATE_SALES,
//                            PermissionCodes.UPDATE,
//                            PermissionCodes.MANAGE_USERS,
//                            PermissionCodes.VIEW_REPORTS,
//                            PermissionCodes.ASSIGN_TARGETS,
//                            PermissionCodes.VIEW_TEAM,
//                            PermissionCodes.CREATE_LEAD,
//                            PermissionCodes.UPDATE_LEAD,
//                            PermissionCodes.CREATE_DEAL,
//                            PermissionCodes.RECORD_SALE
//                    )));
//
//                    System.out.println("Role created: SUPER_ADMIN");
//                    return roleRepository.save(role);
//                });
//    }
//
//    private Role createAdminRole() {
//
//        return roleRepository.findByRoleName("ADMIN")
//                .orElseGet(() -> {
//
//                    Role role = new Role();
//                    role.setRoleName("ADMIN");
//                    role.setDescription("Admin role");
//
//                    role.setPermissions(getPermissions(Set.of(
//                            PermissionCodes.READ_ONLY,
//                            PermissionCodes.ALL_VIEW,
//                            PermissionCodes.CREATE_MANAGER,
//                            PermissionCodes.CREATE_SALES,
//                            PermissionCodes.UPDATE,
//                            PermissionCodes.DELETE,
//                            PermissionCodes.MANAGE_USERS,
//                            PermissionCodes.VIEW_REPORTS
//                    )));
//
//                    System.out.println("Role created: ADMIN");
//                    return roleRepository.save(role);
//                });
//    }
//
//    private Role createManagerRole() {
//
//        return roleRepository.findByRoleName("SALES_MANAGER")
//                .orElseGet(() -> {
//
//                    Role role = new Role();
//                    role.setRoleName("SALES_MANAGER");
//                    role.setDescription("Sales Manager role");
//
//                    role.setPermissions(getPermissions(Set.of(
//                            PermissionCodes.VIEW_TEAM,
//                            PermissionCodes.ASSIGN_TARGETS,
//                            PermissionCodes.VIEW_REPORTS,
//                            PermissionCodes.CREATE_LEAD,
//                            PermissionCodes.UPDATE_LEAD,
//                            PermissionCodes.CREATE_DEAL,
//                            PermissionCodes.RECORD_SALE
//                    )));
//
//                    System.out.println("Role created: SALES_MANAGER");
//                    return roleRepository.save(role);
//                });
//    }
//
//    private Role createViewerRole() {
//
//        return roleRepository.findByRoleName("VIEWER")
//                .orElseGet(() -> {
//
//                    Role role = new Role();
//                    role.setRoleName("VIEWER");
//                    role.setDescription("Read only viewer");
//
//                    role.setPermissions(getPermissions(Set.of(
//                            PermissionCodes.READ_ONLY,
//                            PermissionCodes.VIEW_REPORTS
//                    )));
//
//                    System.out.println("👁️ Role created: VIEWER");
//                    return roleRepository.save(role);
//                });
//    }
//
//    // ======================================================
//    // USERS
//    // ======================================================
//    private void createSuperAdminUser(Role role) {
//        createUserIfNotExists(
//                "Manojlodho0262@gmail.com",
//                "Manoj Lodhi",
//                "Manoj@123",
//                "7223938787",
//                role
//        );
//    }
//
//
//  
//  
//    // ======================================================
//    // COMMON USER METHOD
//    // ======================================================
//    private void createUserIfNotExists(
//            String email,
//            String name,
//            String password,
//            String phone,
//            Role role) {
//
//        if (userRepository.existsByEmail(email)) {
//            System.out.println("User already exists: " + email);
//            return;
//        }
//
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setPhone(phone);
//        user.setStatus(Status.ACTIVE);
//
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
//
//        userRepository.save(user);
//        System.out.println("User created: " + email);
//    }
//    private Set<Permission> getPermissions(Set<String> codes) {
//
//        Set<Permission> permissions = new HashSet<>();
//
//        for (String code : codes) {
//            Permission permission = permissionRepository
//                    .findByPermissionCode(code)
//                    .orElseThrow(() ->
//                            new RuntimeException("Permission not found: " + code));
//
//            permissions.add(permission);
//        }
//        return permissions;
//    }
//}
