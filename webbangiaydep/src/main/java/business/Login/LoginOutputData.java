// business/Login/LoginOutputData.java
package business.Login;

public class LoginOutputData {
    private final boolean success;
    private final String message;
    private final Long userId;
    private final String roleCode; // For authorization

    public LoginOutputData(boolean success, String message, Long userId, String roleCode) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.roleCode = roleCode;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Long getUserId() { return userId; }
    public String getRoleCode() { return roleCode; }
}