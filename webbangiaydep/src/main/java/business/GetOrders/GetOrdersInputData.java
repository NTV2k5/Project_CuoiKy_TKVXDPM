package business.GetOrders;

public class GetOrdersInputData {
    private final String keyword;
    private final String status;

    public GetOrdersInputData(String keyword, String status) {
        this.keyword = keyword;
        this.status = status;
    }

    public String getKeyword() { return keyword; }
    public String getStatus() { return status; }
}