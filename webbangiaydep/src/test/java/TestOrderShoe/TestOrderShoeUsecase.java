// package TestOrderShoe;

// import business.AddToCart.AddToCartRepository;
// import business.OrderShoe.*;
// import business.ProcessPayment.PaymentGatewayRepository;
// import business.entity.Order;
// import presenters.ProcessPayment.ProcessPaymentPresenter;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.math.BigDecimal;
// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class TestOrderShoeUsecase {

//     // 1. Mock các dependency (Interface)
//     @Mock
//     private OrderShoeRepository orderRepository;
//     @Mock
//     private ProductInventoryRepository inventoryRepository;
//     @Mock
//     private OrderShoeOutputBoundary outputBoundary;
//     @Mock
//     private AddToCartRepository cartRepository;
//     @Mock
//     private PaymentGatewayRepository paymentGateway;

//     // Class cần test
//     private OrderShoeUsecase usecase;

//     @BeforeEach
//     void setUp() {
//         // Khởi tạo UseCase với các mock object
//         usecase = new OrderShoeUsecase(orderRepository, inventoryRepository, outputBoundary, cartRepository, null);
//     }

//     // --- Helper để tạo dữ liệu đầu vào mẫu ---
//     private OrderShoeInputData createValidInputData(String paymentMethod) {
//         OrderShoeInputData input = new OrderShoeInputData();
//         input.setUserId(1L);
//         input.setCustomerName("Nguyen Van A");
//         input.setPhone("0909123456");
//         input.setAddress("123 Street, HCM");
//         input.setEmail("test@example.com");
//         input.setPaymentMethod(paymentMethod);

//         List<OrderShoeInputData.RequestItemData> items = new ArrayList<>();
//         OrderShoeInputData.RequestItemData item = new OrderShoeInputData.RequestItemData();
//         item.setProductId(100L);
//         item.setVariantId(10L);
//         item.setQuantity(2);
//         items.add(item);
        
//         input.setItems(items);
//         return input;
//     }
//     // ==========================================
//     // CASE 1: Lỗi - Thiếu thông tin khách hàng
//     // ==========================================
//     @Test
//     void testExecute_Fail_MissingInfo() {
//         OrderShoeInputData input = createValidInputData("COD");
//         input.setPhone(""); // Thiếu SĐT

//         usecase.execute(input);

//         ArgumentCaptor<OrderShoeOutputData> captor = ArgumentCaptor.forClass(OrderShoeOutputData.class);
//         verify(outputBoundary).presentFailure(captor.capture());
//         assertEquals("Vui lòng nhập đầy đủ thông tin nhận hàng", captor.getValue().getMessage());
//     }

//     // ==========================================
//     // CASE 2: Lỗi - Hết hàng (Out of Stock)
//     // ==========================================
//     @Test
//     void testExecute_Fail_OutOfStock() {
//         OrderShoeInputData input = createValidInputData("COD");

//         // Giả lập checkStock trả về false
//         when(inventoryRepository.checkStock(anyLong(), anyLong(), anyInt())).thenReturn(false);

//         usecase.execute(input);

//         ArgumentCaptor<OrderShoeOutputData> captor = ArgumentCaptor.forClass(OrderShoeOutputData.class);
//         verify(outputBoundary).presentFailure(captor.capture());
        
//         assertTrue(captor.getValue().getMessage().contains("không đủ số lượng"));
        
//         // Đảm bảo KHÔNG lưu đơn hàng và KHÔNG trừ kho
//         verify(orderRepository, never()).save(any());
//         verify(inventoryRepository, never()).reduceStock(anyLong(), anyLong(), anyInt());
//     }
//        // ==========================================
//     // CASE 3: Đặt hàng thành công (COD)
//     // ==========================================
//     @Test
//     void testExecute_SuccessfulOrder_COD() {
//         // Arrange (Chuẩn bị)
//         OrderShoeInputData input = createValidInputData("COD");

//         // Giả lập kho còn hàng
//         when(inventoryRepository.checkStock(anyLong(), anyLong(), anyInt())).thenReturn(true);
        
//         // Giả lập thông tin sản phẩm (Giá 500k)
//         ProductInventoryRepository.ProductInfo productInfo = 
//             new ProductInventoryRepository.ProductInfo("Giày Nike", new BigDecimal("500000"));
//         when(inventoryRepository.getProductInfo(anyLong(), anyLong())).thenReturn(productInfo);

//         // Giả lập lưu đơn hàng thành công và trả về Order có ID
//         when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
//             Order order = invocation.getArgument(0);
//             order.setId(999L);
//             return order;
//         });

//         // Act (Thực thi)
//         usecase.execute(input);

//         // Assert (Kiểm tra)
//         // 1. Kiểm tra đã trừ kho chưa
//         verify(inventoryRepository).reduceStock(100L, 10L, 2);

//         // 2. Kiểm tra Output trả về Success
//         ArgumentCaptor<OrderShoeOutputData> captor = ArgumentCaptor.forClass(OrderShoeOutputData.class);
//         verify(outputBoundary).presentSuccess(captor.capture());

//         OrderShoeOutputData result = captor.getValue();
//         assertEquals("SUCCESS", result.getStatus());
//         assertEquals(999L, result.getOrderId());
//         assertEquals(new BigDecimal("1000000"), result.getTotalAmount());
//     }

//     // ==========================================
//     // CASE 4: Đặt hàng thành công (Online/QR)
//     // ==========================================
//     @Test
//     void testExecute_SuccessfulOrder_Online() {
//         // Arrange
//         OrderShoeInputData input = createValidInputData("ONLINE");

//         when(inventoryRepository.checkStock(anyLong(), anyLong(), anyInt())).thenReturn(true);
//         when(inventoryRepository.getProductInfo(anyLong(), anyLong()))
//             .thenReturn(new ProductInventoryRepository.ProductInfo("Giày Adidas", new BigDecimal("200000")));
        
//         when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
//             Order o = inv.getArgument(0);
//             o.setId(888L);
//             return o;
//         });

//         // Act
//         usecase.execute(input);

//         // Assert
//         ArgumentCaptor<OrderShoeOutputData> captor = ArgumentCaptor.forClass(OrderShoeOutputData.class);
//         verify(outputBoundary).presentSuccess(captor.capture());

//         OrderShoeOutputData result = captor.getValue();
//         // Với Online, trạng thái phải là PENDING_PAYMENT
//         assertEquals("PENDING_PAYMENT", result.getStatus());
//         assertEquals("Vui lòng thanh toán qua QR để hoàn tất đơn hàng.", result.getMessage());
//     }

// }