// business/UpdateProduct/UpdateProductInputBoundary.java
package business.UpdateProduct;

public interface UpdateProductInputBoundary {
    void execute(UpdateProductInputData input, UpdateProductOutputBoundary presenter);
}