package presenters.UpdateUser;

public class UpdateUserViewModel {
    public final boolean success;
    public final String message;

    public UpdateUserViewModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}