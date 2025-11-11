// business/AddShoe/AddShoeOutputData.java
package business.AddShoe;

public class AddShoeOutputData {
    private final boolean success;
    private final String message;
    private final long newId; // If success

    public AddShoeOutputData(boolean success, String message, long newId) {
        this.success = success;
        this.message = message;
        this.newId = newId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public long getNewId() { return newId; }
}