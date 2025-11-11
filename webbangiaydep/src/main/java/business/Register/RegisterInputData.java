// business/Register/RegisterInputData.java
package business.Register;

public class RegisterInputData {
    private final String email;
    private final String password;
    private final String fullName;
    private final String phone;
    private final int roleId; // Default to CUSTOMER (2)

    public RegisterInputData(String email, String password, String fullName, String phone, int roleId) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.roleId = roleId;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public int getRoleId() { return roleId; }
}