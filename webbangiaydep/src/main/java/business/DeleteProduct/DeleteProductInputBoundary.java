// business/DeleteProduct/DeleteProductInputBoundary.java
package business.DeleteProduct;

public interface DeleteProductInputBoundary {
    void execute(DeleteProductInputData input, DeleteProductOutputBoundary presenter);
}