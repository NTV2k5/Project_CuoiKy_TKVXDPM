// persistence/ViewShoeDetail/ViewShoeDetailDTO.java
package persistence.ViewShoeDetail;

import java.util.List;

public class ViewShoeDetailDTO 
{
    public Long id;
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
        public Long variantId;
        public String size;
        public String color;
        public String hexCode;
        public int stock;
        public double price;
        public boolean status;
    }
}