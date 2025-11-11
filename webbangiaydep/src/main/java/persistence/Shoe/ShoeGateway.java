// persistence/Shoe/ShoeGateway.java
package persistence.Shoe;

import java.util.List;

public interface ShoeGateway {
    // For Search
    List<ShoeDTO> searchByKeyword(String keyword);
    // For Add
    boolean insertShoe(ShoeDTO shoe);
    // For Update
    boolean updateShoe(ShoeDTO shoe);
    // For Delete
    boolean deleteShoe(long id);
    // For Get by ID (used in Update/Delete)
    ShoeDTO findById(long id);
    // For List all (for dashboard)
    List<ShoeDTO> findAll();
}