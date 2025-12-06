package presenters.ProcessPayment;

import business.ProcessPayment.ProcessPaymentOutputBoundary;
import business.ProcessPayment.ProcessPaymentOutputData;

public class ProcessPaymentPresenter implements ProcessPaymentOutputBoundary
{
private final ProcessPaymentViewModel viewModel = new ProcessPaymentViewModel();

    @Override
    public void presentSuccess(ProcessPaymentOutputData output) {
        viewModel.success = output.isSuccess();
        viewModel.message = output.getMessage();
        viewModel.orderId = output.getOrderId();
    }

    @Override
    public void presentFailure(ProcessPaymentOutputData output) {
        viewModel.success = output.isSuccess(); // false
        viewModel.message = output.getMessage();
        viewModel.orderId = output.getOrderId();
    }

    public ProcessPaymentViewModel getViewModel() {
        return viewModel;
    }
}
