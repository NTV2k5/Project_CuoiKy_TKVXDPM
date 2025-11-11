// business/UpdateUser/UpdateUserInputData.java
package business.UpdateUser;

public class UpdateUserInputData {
    public final long id;
    public final String fullName;
    public final String phone;
    public final int roleId;
    public final boolean isActive;

    public UpdateUserInputData(long id, String fullName, String phone, int roleId, boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.roleId = roleId;
        this.isActive = isActive;
    }
}