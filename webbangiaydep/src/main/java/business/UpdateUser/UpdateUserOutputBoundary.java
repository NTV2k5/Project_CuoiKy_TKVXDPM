package business.UpdateUser;

public interface UpdateUserOutputBoundary {
    void presentSuccess();
    void presentError(String message);
}