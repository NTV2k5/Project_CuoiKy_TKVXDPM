package TestViewShoeList;

import business.entity.Shoe;
import business.entity.ShoeVariant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestShoe {

    @Test
    //Constructor mặc định khởi tạo các thuộc tính với giá trị mặc định
    public void testDefaultConstructor() {
        Shoe shoe = new Shoe();

        // Kiểm tra giá trị mặc định
        assertEquals(0, shoe.getId());
        assertNull(shoe.getName());
        assertNull(shoe.getDescription());
        assertEquals(0.0, shoe.getPrice());
        assertEquals(0, shoe.getStockQuantity());
        assertNull(shoe.getImageUrl());
        assertNull(shoe.getBrand());
        assertNull(shoe.getCategory());
        assertFalse(shoe.isActive());
        assertNotNull(shoe.getVariants()); // đảm bảo danh sách không null
        assertEquals(0, shoe.getVariants().size());
    }

    @Test
    //Constructor có tham số khởi tạo các thuộc tính chính xác
    public void testParameterizedConstructor() {
        Shoe shoe = new Shoe(
            1, 
            "Sneaker XYZ", 
            "Giày thể thao thoáng khí", 
            1200000.0, 
            50, 
            "image.jpg", 
            "Nike", 
            "Thể thao", 
            true
        );

        assertEquals(1, shoe.getId());
        assertEquals("Sneaker XYZ", shoe.getName());
        assertEquals("Giày thể thao thoáng khí", shoe.getDescription());
        assertEquals(1200000.0, shoe.getPrice());
        assertEquals(50, shoe.getStockQuantity());
        assertEquals("image.jpg", shoe.getImageUrl());
        assertEquals("Nike", shoe.getBrand());
        assertEquals("Thể thao", shoe.getCategory());
        assertTrue(shoe.isActive());
    }

    @Test
    //Getter và Setter đặt và lấy giá trị đúng
    public void testSettersAndGetters() {
        Shoe shoe = new Shoe();

        shoe.setId(2);
        shoe.setName("Sandal ABC");
        shoe.setDescription("Giày sandal nữ thời trang");
        shoe.setPrice(550000.0);
        shoe.setStockQuantity(30);
        shoe.setImageUrl("sandal.jpg");
        shoe.setBrand("Bitis");
        shoe.setCategory("Sandal");
        shoe.setActive(true);

        assertEquals(2, shoe.getId());
        assertEquals("Sandal ABC", shoe.getName());
        assertEquals("Giày sandal nữ thời trang", shoe.getDescription());
        assertEquals(550000.0, shoe.getPrice());
        assertEquals(30, shoe.getStockQuantity());
        assertEquals("sandal.jpg", shoe.getImageUrl());
        assertEquals("Bitis", shoe.getBrand());
        assertEquals("Sandal", shoe.getCategory());
        assertTrue(shoe.isActive());
    }

    @Test
    //Thêm biến thể và tính tổng tồn kho đúng không
    public void testAddVariantAndCalculateStock() {
        Shoe shoe = new Shoe();
        ShoeVariant v1 = new ShoeVariant("42", "Đỏ", "#FF0000", 10);
        ShoeVariant v2 = new ShoeVariant("43", "Xanh", "#0000FF", 5);
        
        shoe.addVariant(v1);
        shoe.addVariant(v2);
        
        assertEquals(2, shoe.getVariants().size());
        assertEquals(15, shoe.calculateTotalStock());
    }
}
