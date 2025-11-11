// persistence/Category/CategoryDTO.java
package persistence.Category;

public class CategoryDTO {
    private final int id;
    private final  String code;
    private final String name;
    private final  String description;
    private final Integer parentId;

    public CategoryDTO(int id, String code, String name, String description, Integer parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    // Getters
    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getParentId() { return parentId; }
}