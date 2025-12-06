// presenters/ViewShoeDetail/ViewShoeDetailItem.java
package presenters.ViewShoeDetail;

import java.util.List;

public class ViewShoeDetailItem 
{
    public Long id;
    public String name;
    public double price;
    public String imageUrl;
    public String brand;
    public String category;
    public String description;
    public boolean isActive;
    public List<Variant> variants;

    public static class Variant {
        public Long variantId;
        public String size;
        public String color;
        public String hexCode;
        public int stock;
        public String status;
        public boolean outOfStock;
    }
}