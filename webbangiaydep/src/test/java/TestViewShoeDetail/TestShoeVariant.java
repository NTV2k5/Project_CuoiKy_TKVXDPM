package TestViewShoeDetail;

import business.entity.ShoeVariant;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestShoeVariant {

    @Test
    public void testIsInStock() {
        ShoeVariant variant = new ShoeVariant("42", "Đỏ", "#FF0000", 10);

        assertTrue(variant.isInStock(5));   // còn đủ
        assertTrue(variant.isInStock(10));  // đúng bằng tồn kho
        assertFalse(variant.isInStock(15)); // không đủ
    }

    @Test
    public void testGetters() {
        ShoeVariant variant = new ShoeVariant("42", "Đỏ", "#FF0000", 10);
        assertEquals("42", variant.getSize());
        assertEquals("Đỏ", variant.getColor());
        assertEquals("#FF0000", variant.getHexCode());
        assertEquals(10, variant.getStock());
    }
}
