// src/main/java/presenters/Order/UpdateOrderStatusPresenter.java
package presenters.UpdateOrderStatus;

import business.UpdateOrderStatus.UpdateOrderStatusOutputBoundary;
import business.UpdateOrderStatus.UpdateOrderStatusOutputData;

public class UpdateOrderStatusPresenter implements UpdateOrderStatusOutputBoundary {
    private UpdateOrderStatusViewModel viewModel;

    @Override
    public void present(UpdateOrderStatusOutputData output) {
        this.viewModel = new UpdateOrderStatusViewModel(
            output.isSuccess(),
            output.getMessage()
        );
    }

    public UpdateOrderStatusViewModel getViewModel() {
        return viewModel;
    }
}