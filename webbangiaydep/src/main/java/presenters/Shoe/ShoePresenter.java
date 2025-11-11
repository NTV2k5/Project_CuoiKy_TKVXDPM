// // presenters/Shoe/ShoePresenter.java (Generic for all, or separate)
// package presenters.Shoe;

// // Example for Add
// public class AddShoePresenter implements AddShoeOutputBoundary {
//     private AddShoeViewModel viewModel;

//     @Override
//     public void present(AddShoeOutputData output) {
//         viewModel = new AddShoeViewModel();
//         viewModel.success = output.isSuccess();
//         viewModel.message = output.getMessage();
//     }

//     public AddShoeViewModel getViewModel() { return viewModel; }
// }

// public class AddShoeViewModel {
//     public boolean success;
//     public String message;
// }