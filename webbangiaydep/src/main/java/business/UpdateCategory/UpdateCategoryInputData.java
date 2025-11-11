// business/UpdateCategory/UpdateCategoryInputData.java
package business.UpdateCategory;

public class UpdateCategoryInputData {
    private final long id;
    private final String code;
    private final String name;
    private final String description;
    private final Integer parentId;

    public UpdateCategoryInputData(long id, String code, String name, String description, Integer parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getParentId() { return parentId; }
}