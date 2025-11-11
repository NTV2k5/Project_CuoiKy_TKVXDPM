// business/AddUser/AddUserInputData.java
package business.AddUser;

public class AddUserInputData {
    public final String email;
    public final String password;
    public final String fullName;
    public final String phone;
    public final int roleId;

    public AddUserInputData(String email, String password, String fullName, String phone, int roleId) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.roleId = roleId;
    }
}