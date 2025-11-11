package presenters.GetOrders;

import business.GetOrders.GetOrdersOutputBoundary;
import business.GetOrders.GetOrdersOutputData;

public class GetOrdersPresenter implements GetOrdersOutputBoundary {
    private GetOrdersViewModel viewModel;

    @Override
    public void present(GetOrdersOutputData output) {
        if (output.isSuccess()) {
            this.viewModel = new GetOrdersViewModel(
                output.getOrders(),
                output.getTotalCount(),
                null
            );
        } else {
            this.viewModel = new GetOrdersViewModel(
                null,
                0,
                output.getError()
            );
        }
    }

    public GetOrdersViewModel getViewModel() {
        return viewModel;
    }
}