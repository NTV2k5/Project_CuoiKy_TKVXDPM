package presenters.OrderShoe;

import business.OrderShoe.*;

public class OrderShoePresenter implements OrderShoeOutputBoundary {
    private OrderShoeViewModel viewModel = new OrderShoeViewModel();

    @Override
    public void presentSuccess(OrderShoeOutputData output) 
    {
        viewModel.setStatus(output.getStatus());
        viewModel.setMessage(output.getMessage());
        viewModel.setOrderId(output.getOrderId());
        viewModel.setTotalAmount(output.getTotalAmount());
        viewModel.setPaymentUrl(output.getPaymentUrl());
    }

    @Override
    public void presentFailure(OrderShoeOutputData output) {
        viewModel.setStatus(output.getStatus());
        viewModel.setMessage(output.getMessage());
    }

    public OrderShoeViewModel getViewModel() {
        return viewModel;
    }
}