package business.AddToCart;

public class AddToCart 
{
    private  int userId;
    private  int productId;
    private  int variantId;
    private  int quantity;
    
	public AddToCart(int userId, int productId, int variantId, int quantity) 
    {
		this.userId = userId;
		this.productId = productId;
		this.variantId = variantId;
		this.quantity = quantity;
	}
	public int getUserId() {
		return userId;
	}
	public int getProductId() {
		return productId;
	}
	public int getVariantId() {
		return variantId;
	}
	public int getQuantity() {
		return quantity;
	}
    public static void checkInputQuantity(int quantity)
    {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
    }
    public static void checkInputUserId(Long userID)
    {
        if(userID == null)
        throw new IllegalArgumentException("Yêu Cầu Đăng nhập trước khi thêm vào giỏ hàng");
    }
	public static void checkInputVariant(Long variantID)
	{
		if(variantID < 0)
		{
			throw new IllegalArgumentException("Variant ID không hợp lệ");
		}
	}
}
