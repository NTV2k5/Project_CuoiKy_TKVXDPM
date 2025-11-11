
package business.DeleteOrder;

public interface DeleteOrderInputBoundary {
    void execute(DeleteOrderInputData input, DeleteOrderOutputBoundary presenter);
}