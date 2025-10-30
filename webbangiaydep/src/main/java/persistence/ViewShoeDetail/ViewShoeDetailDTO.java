// persistence/ViewShoeDetail/ViewShoeDetailDTO.java
package persistence.ViewShoeDetail;

import java.util.List;

public class ViewShoeDetailDTO 
{
    public int id;
    public String name;
    public double price;
    public String description;
    public String imageUrl;
    public String brand;
    public String category;
    public List<Variant> variants;

    public static class Variant {
        public String size;
        public String color;
        public String hexCode;
        public int stock;
    }
}