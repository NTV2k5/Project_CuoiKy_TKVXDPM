// src/main/java/persistence/User/UserDTO.java
package persistence.User;

public class UserDTO {
    private final long id;
    private final String email;
    private final String fullName;
    private final String phone;
    private final int roleId;
    private final String roleCode;
    private final boolean isActive; // ĐÃ CÓ

    public UserDTO(long id, String email, String fullName, String phone, int roleId, String roleCode, boolean isActive) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.isActive = isActive;
    }

    // === CÁC GETTER KHÁC ===
    public long getId() { return id; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public int getRoleId() { return roleId; }
    public String getRoleCode() { return roleCode; }

    // === THIẾU: GETTER CHO isActive ===
    public boolean isActive() { return isActive; } // THÊM DÒNG NÀY
}