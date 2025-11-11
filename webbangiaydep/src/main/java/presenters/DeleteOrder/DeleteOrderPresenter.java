// src/main/java/presenters/Order/DeleteOrderPresenter.java
package presenters.DeleteOrder;

import business.DeleteOrder.DeleteOrderOutputBoundary;
import business.DeleteOrder.DeleteOrderOutputData;

public class DeleteOrderPresenter implements DeleteOrderOutputBoundary {
    private DeleteOrderViewModel viewModel;

    @Override
    public void present(DeleteOrderOutputData output) {
        this.viewModel = new DeleteOrderViewModel(output.isSuccess(), output.getMessage());
    }

    public DeleteOrderViewModel getViewModel() { return viewModel; }
}