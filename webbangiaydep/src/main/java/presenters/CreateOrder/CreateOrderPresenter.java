// src/main/java/presenters/Order/CreateOrderPresenter.java
package presenters.CreateOrder;

import business.CreateOrder.CreateOrderOutputBoundary;
import business.CreateOrder.CreateOrderOutputData;

public class CreateOrderPresenter implements CreateOrderOutputBoundary {
    private CreateOrderViewModel viewModel;

    @Override
    public void present(CreateOrderOutputData output) {
        this.viewModel = new CreateOrderViewModel(
            output.isSuccess(),
            output.getOrderId(),
            output.getMessage()
        );
    }

    public CreateOrderViewModel getViewModel() { return viewModel; }
}