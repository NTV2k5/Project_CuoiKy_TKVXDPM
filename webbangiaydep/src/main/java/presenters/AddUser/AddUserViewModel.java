package presenters.AddUser;

public class AddUserViewModel {
    public final boolean success;
    public final String message;

    public AddUserViewModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}