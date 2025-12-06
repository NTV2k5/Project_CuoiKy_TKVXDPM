package business.ProcessPayment;

public interface ProcessPaymentInputBoundary {
    void execute(ProcessPaymentInputData input, ProcessPaymentOutputBoundary presenter);
}