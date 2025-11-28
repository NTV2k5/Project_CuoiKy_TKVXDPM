// persistence/ViewShoeDetail/ViewShoeDetailDTO.java
package persistence.ViewShoeCart;

import java.util.List;

public class ShoeVariantDTO 
{
    public int id;
    public String name;
    public double price;
    public String description;
    public String imageUrl;
    public String brand;
    public String category;
    public int isActive;
    public List<Variant> variants;

    public static class Variant 
    {
        public int variantId;
        public String size;
        public String color;
        public String hexCode;
        public int stock;
        public double price;
        public boolean status;
    }
}