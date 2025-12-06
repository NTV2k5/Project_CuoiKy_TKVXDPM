package persistence.Adapter;

import business.ProcessPayment.PaymentGatewayRepository;
import business.entity.Order;
import infrastructure.VNPay.VNPayConfig;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VNPayGatewayImpl implements PaymentGatewayRepository {

    @Override
    public String createPaymentUrl(Order order, String ipAddress) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = String.valueOf(order.getId());
        
        // Nhân 100 để đổi sang đơn vị VND (VNPAY quy định)
        long amount = order.getTotal().longValue() * 100;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddress);
        // vnp_Params.put("vnp_BankCode", ""); // Có thể bỏ qua nếu không chọn trước ngân hàng
        
        // Thời gian tạo
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        // Thời gian hết hạn
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // --- XỬ LÝ TẠO URL VÀ CHECKSUM ---
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                
                // 1. Xây dựng chuỗi Hash: Dữ liệu GỐC, KHÔNG Encode
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue); // <--- QUAN TRỌNG: Giữ nguyên value gốc
                
                // 2. Xây dựng Query URL: Dữ liệu CẦN Encode
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        
        String queryUrl = query.toString();
        // Tạo SecureHash từ chuỗi hashData (dữ liệu gốc)
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        
        // Ghép chuỗi cuối cùng
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnp_SecureHash;
        
        // Debug log để kiểm tra
        System.out.println("--- DEBUG VNPAY ---");
        System.out.println("HashData (Raw): " + hashData.toString());
        System.out.println("SecureHash: " + vnp_SecureHash);
        System.out.println("Final URL: " + paymentUrl);
        
        return paymentUrl;
    }
}