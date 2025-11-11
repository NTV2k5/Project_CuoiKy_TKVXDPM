package business.AddUser;

public class AddUserOutputData {
    public final boolean success;
    public final String message;

    public AddUserOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}