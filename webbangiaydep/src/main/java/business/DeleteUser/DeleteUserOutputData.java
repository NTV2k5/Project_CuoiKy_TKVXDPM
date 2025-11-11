package business.DeleteUser;

public class DeleteUserOutputData {
    public final boolean success;
    public final String message;

    public DeleteUserOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}