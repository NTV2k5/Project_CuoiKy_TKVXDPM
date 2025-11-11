// src/main/java/persistence/User/UserGateway.java
package persistence.User;

import java.util.List;

public interface UserGateway {
    // === Admin Management ===
    List<UserDTO> getAllUsers();
    List<UserDTO> searchUsers(String keyword, String role, int limit);
    int getTotalUserCount(String keyword, String role);
    UserDTO getUserById(long id);
    long createUser(String email, String password, int roleId, String fullName, String phone);
    void updateUser(long id, String fullName, String phone, int roleId, boolean isActive);
    void deleteUser(long id);
    boolean emailExists(String email);
    boolean emailExistsExcept(long userId, String email);

    // === Login & Register ===
    Long authenticate(String email, String password);
    String getRoleCodeByUserId(Long userId);
}