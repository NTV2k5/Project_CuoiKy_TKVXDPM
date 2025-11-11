package business.UpdateUser;

public class UpdateUserOutputData {
    public final boolean success;
    public final String message;

    public UpdateUserOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}