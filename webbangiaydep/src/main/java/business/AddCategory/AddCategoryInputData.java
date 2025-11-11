// business/AddCategory/AddCategoryInputData.java
package business.AddCategory;

public class AddCategoryInputData {
    private final String code;
    private final String name;
    private final String description;
    private final Integer parentId;

    public AddCategoryInputData(String code, String name, String description, Integer parentId) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getParentId() { return parentId; }
}