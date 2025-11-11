// business/AddShoe/AddShoeUseCase.java
package business.AddShoe;

import persistence.Shoe.ShoeDTO;
import persistence.Shoe.ShoeGateway;

public class AddShoeUseCase implements AddShoeInputBoundary {
    private final ShoeGateway shoeGateway;

    public AddShoeUseCase(ShoeGateway shoeGateway) {
        this.shoeGateway = shoeGateway;
    }

    @Override
    public void execute(AddShoeInputData input, AddShoeOutputBoundary presenter) {
        // Validate (SRP: simple checks)
        if (input.getSku() == null || input.getSku().trim().isEmpty()) {
            presenter.present(new AddShoeOutputData(false, "SKU không được để trống", 0));
            return;
        }
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            presenter.present(new AddShoeOutputData(false, "Tên giày không được để trống", 0));
            return;
        }
        if (input.getPrice() <= 0 || input.getStock() < 0) {
            presenter.present(new AddShoeOutputData(false, "Giá phải > 0, tồn kho >= 0", 0));
            return;
        }

        // Check SKU unique
        // (Assume gateway has method, but for simplicity, insert and catch exception)
        ShoeDTO dto = new ShoeDTO();
        dto.sku = input.getSku();
        dto.name = input.getName();
        dto.shortDescription = input.getShortDescription();
        dto.description = input.getDescription();
        dto.price = input.getPrice();
        dto.stock = input.getStock();
        dto.imageUrl = input.getImageUrl();
        dto.brand = input.getBrand();
        dto.size = input.getSize();
        dto.color = input.getColor();
        dto.categoryId = input.getCategoryId();
        dto.isActive = input.isActive();

        try {
            boolean inserted = shoeGateway.insertShoe(dto);
            if (inserted) {
                presenter.present(new AddShoeOutputData(true, "Thêm giày thành công", dto.id)); // Assume ID returned, adjust if needed
            } else {
                presenter.present(new AddShoeOutputData(false, "Lỗi thêm giày", 0));
            }
        } catch (Exception e) {
            presenter.present(new AddShoeOutputData(false, "SKU đã tồn tại hoặc lỗi hệ thống", 0));
        }
    }
}