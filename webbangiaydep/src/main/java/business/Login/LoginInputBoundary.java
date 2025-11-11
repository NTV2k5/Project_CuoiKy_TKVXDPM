// business/Login/LoginInputBoundary.java
package business.Login;

public interface LoginInputBoundary {
    void execute(LoginInputData input, LoginOutputBoundary presenter);
}