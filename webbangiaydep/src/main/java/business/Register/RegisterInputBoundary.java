// business/Register/RegisterInputBoundary.java
package business.Register;

public interface RegisterInputBoundary {
    void execute(RegisterInputData input, RegisterOutputBoundary presenter);
}