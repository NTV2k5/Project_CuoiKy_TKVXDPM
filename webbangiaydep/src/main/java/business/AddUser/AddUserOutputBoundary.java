package business.AddUser;

public interface AddUserOutputBoundary {
    void presentSuccess();
    void presentError(String message);
}
