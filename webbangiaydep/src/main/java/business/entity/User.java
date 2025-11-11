package business.entity;

public class User {
    private long id;
    private String email;
    private String password; // Hashed
    private int roleId;
    private String fullName;
    private String phone;
    private boolean isActive;

    public User() {}

    public User(long id, String email, String password, int roleId, String fullName, String phone, boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.fullName = fullName;
        this.phone = phone;
        this.isActive = isActive;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}