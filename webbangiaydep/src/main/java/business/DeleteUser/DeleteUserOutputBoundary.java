package business.DeleteUser;

public interface DeleteUserOutputBoundary {
    void presentSuccess();
    void presentError(String message);
}