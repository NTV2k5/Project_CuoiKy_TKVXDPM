<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  
    // LẤY DỮ LIỆU 1 LẦN DUY NHẤT – ĐÂY LÀ BIẾN CHÍNH!!!
    presenters.ViewShoeDetail.ViewShoeDetailItem shoeDetailItem = 
        (presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%= shoeDetailItem != null ? shoeDetailItem.name : "Sản phẩm" %>
    </title>
    <link rel="stylesheet" href="detailShoe.css">
</head>
<body>
<div class="container">
    <% if (shoeDetailItem != null) { %>
        <div class="product-card">
            <!-- Ảnh -->
            <div class="product-image">
                <img src="<%= request.getContextPath() %>/images/<%= shoeDetailItem.imageUrl %>" 
                     alt="<%= shoeDetailItem.name %>">
            </div>

            <!-- Thông tin -->
            <div class="product-info">
                <h1 class="product-title"><%= shoeDetailItem.name %></h1>
                <p><strong>Hãng:</strong> <%= shoeDetailItem.brand %></p>
                <p><strong>Danh mục:</strong> <%= shoeDetailItem.category %></p>
                <p><strong>Giá:</strong> <span class="price"><%= shoeDetailItem.price %> VND</span></p>
                <p><strong>Mô tả:</strong> <%= shoeDetailItem.description %></p>

                <!-- SIZE & COLOR -->
                <% if (shoeDetailItem.variants != null && !shoeDetailItem.variants.isEmpty()) { %>
                    <div class="variant-group">
                        <p><strong>Size:</strong></p>
                        <div class="size-options">
                            <%
                                java.util.Set<String> displayedSizes = new java.util.HashSet<>();
                                int sizeIndex = 0;
                                for (presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v : shoeDetailItem.variants) {
                                    if (displayedSizes.add(v.size)) {
                                        boolean inStock = v.stock > 0;
                                        String sizeId = "size-" + sizeIndex++;
                            %>
                                        <label class="size-btn <%= inStock ? "" : "disabled" %>" 
                                               for="<%= sizeId %>">
                                            <input type="radio" 
                                                   id="<%= sizeId %>" 
                                                   name="size" 
                                                   value="<%= v.size %>" 
                                                   <%= inStock ? "" : "disabled" %> />
                                            <%= v.size %>
                                            <% if (!inStock) { %><span class="out">Hết</span><% } %>
                                        </label>
                            <%
                                    }
                                }
                            %>
                        </div>

                        <p><strong>Màu:</strong></p>
                        <div class="color-options">
                            <%
                                java.util.Set<String> displayedColors = new java.util.HashSet<>();
                                int colorIndex = 0;
                                for (presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v : shoeDetailItem.variants) {
                                    if (displayedColors.add(v.color)) {
                                        String colorId = "color-" + colorIndex++;
                            %>
                                        <label class="color-text-btn" for="<%= colorId %>">
                                            <input type="radio" 
                                                   id="<%= colorId %>" 
                                                   name="color" 
                                                   value="<%= v.color %>" />
                                            <%= v.color %>
                                        </label>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                <% } else { %>
                    <p><em>Không có biến thể size/màu.</em></p>
                <% } %>

                <!-- Số lượng -->
                <div class="quantity-group">
                    <p><strong>Số lượng:</strong></p>
                    <div class="quantity-controls">
                        <button type="button" class="qty-btn minus">-</button>
                        <input type="number" id="quantity" name="quantity" value="1" min="1" readonly>
                        <button type="button" class="qty-btn plus">+</button>
                    </div>
                </div>

                <a href="index.jsp" class="btn-back">Quay lại trang chủ</a>
                
                <button type="button" 
                        class="btn-add-cart" 
                        id="addToCartBtn"
                        data-productid="<%= shoeDetailItem.id %>"        
                        data-productprice="<%= shoeDetailItem.price %>">
                    Thêm vào giỏ
                </button>
            </div>
        </div>
    <% } else { %>
        <div class="not-found">
            <p>Không tìm thấy sản phẩm!</p>
            <a href="index.jsp" class="btn-back">Quay lại</a>
        </div>
    <% } %>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    // TẠO MẢNG BIẾN THỂ TỪ JSP – BÂY GIỜ DÙNG ĐÚNG shoeDetailItem
    const variants = [
        <% if (shoeDetailItem != null && shoeDetailItem.variants != null) { 
            for (int i = 0; i < shoeDetailItem.variants.size(); i++) { 
                presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v = shoeDetailItem.variants.get(i); %>
        {
            variantId: <%= v.variantId %>,
            size: "<%= v.size %>",
            color: "<%= v.color %>",
            stock: <%= v.stock %>
        }<%= i < shoeDetailItem.variants.size()-1 ? "," : "" %>
        <% } } %>
    ];

    console.log("Danh sách biến thể:", variants);

    // XỬ LÝ SIZE, MÀU
    document.querySelectorAll('.size-btn').forEach(label => {
        const input = label.querySelector('input');
        label.addEventListener('click', () => {
            if (input && input.disabled) return;
            document.querySelectorAll('.size-btn').forEach(l => l.classList.remove('selected'));
            label.classList.add('selected');
        });
        if (input && input.checked) label.classList.add('selected');
    });

    document.querySelectorAll('.color-text-btn').forEach(label => {
        const input = label.querySelector('input');
        label.addEventListener('click', () => {
            document.querySelectorAll('.color-text-btn').forEach(l => l.classList.remove('selected'));
            label.classList.add('selected');
        });
        if (input && input.checked) label.classList.add('selected');
    });

    // Tự động chọn size/màu đầu tiên
    const firstSize = document.querySelector('.size-btn:not(.disabled) input');
    if (firstSize && !document.querySelector('input[name="size"]:checked')) {
        firstSize.checked = true;
        firstSize.closest('.size-btn').classList.add('selected');
    }
    const firstColor = document.querySelector('.color-text-btn input');
    if (firstColor && !document.querySelector('input[name="color"]:checked')) {
        firstColor.checked = true;
        firstColor.closest('.color-text-btn').classList.add('selected');
    }

    // Nút +/- số lượng
    document.querySelector('.qty-btn.plus').onclick = () => {
        const q = document.getElementById('quantity');
        q.value = parseInt(q.value) + 1;
    };
    document.querySelector('.qty-btn.minus').onclick = () => {
        const q = document.getElementById('quantity');
        if (q.value > 1) q.value = parseInt(q.value) - 1;
    };

    // THÊM VÀO GIỎ HÀNG
    document.getElementById('addToCartBtn')?.addEventListener('click', function() {
        const sizeRadio = document.querySelector('input[name="size"]:checked');
        const colorRadio = document.querySelector('input[name="color"]:checked');
        const quantity = parseInt(document.getElementById('quantity').value) || 1;

        if (!sizeRadio || !colorRadio) {
            alert('Vui lòng chọn đầy đủ size và màu!');
            return;
        }

        const selected = variants.find(v => v.size === sizeRadio.value && v.color === colorRadio.value);

        if (!selected) {
            alert('Không tìm thấy sản phẩm này trong kho!');
            return;
        }
        if (selected.stock < quantity) {
            alert(`Chỉ còn ${selected.stock} sản phẩm!`);
            return;
        }

        const formData = new URLSearchParams();
        formData.append('productId', '<%= shoeDetailItem.id %>');
        formData.append('variantId', selected.variantId);
        formData.append('quantity', quantity);

        console.log("Đang gửi:", formData.toString());

        fetch('<%= request.getContextPath() %>/AddToCart', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData
        })
        .then(r => r.json())
        .then(data => {
            if (data.success) {
                alert(data.message || 'Thêm vào giỏ thành công!');
                const cartCount = document.getElementById('cartCount');
                if (cartCount) cartCount.textContent = data.totalItems;
            } else {
                alert('Lỗi: ' + data.message);
            }
        })
        .catch(() => alert('Lỗi kết nối server!'));
    });
});
</script>
</body>
</html>