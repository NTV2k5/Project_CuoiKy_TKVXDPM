package business.ProcessPayment;

public interface ProcessPaymentOutputBoundary {
    void presentSuccess(ProcessPaymentOutputData output);
    void presentFailure(ProcessPaymentOutputData output);
}