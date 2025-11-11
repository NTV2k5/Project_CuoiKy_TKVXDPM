// business/AddProduct/AddProductInputBoundary.java
package business.AddProduct;

public interface AddProductInputBoundary {
    void execute(AddProductInputData input, AddProductOutputBoundary presenter);
}