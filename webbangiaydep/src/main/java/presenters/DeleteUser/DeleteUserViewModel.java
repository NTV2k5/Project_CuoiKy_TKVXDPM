package presenters.DeleteUser;

public class DeleteUserViewModel {
    public final boolean success;
    public final String message;

    public DeleteUserViewModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
