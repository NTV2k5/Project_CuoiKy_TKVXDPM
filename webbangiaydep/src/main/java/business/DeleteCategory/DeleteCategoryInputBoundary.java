// business/DeleteCategory/DeleteCategoryInputBoundary.java
package business.DeleteCategory;

public interface DeleteCategoryInputBoundary {
    void execute(DeleteCategoryInputData input, DeleteCategoryOutputBoundary presenter);
}