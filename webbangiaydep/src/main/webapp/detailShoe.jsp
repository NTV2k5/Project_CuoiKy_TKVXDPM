<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%= 
            ((presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail")) != null ?
            ((presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail")).name :
            "Sản phẩm"
        %>
    </title>
    <link rel="stylesheet" href="detailShoe.css">
</head>
<body>
<%  
    presenters.ViewShoeDetail.ViewShoeDetailItem shoe =
        (presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail");
%>
<div class="container">
    <% if (shoe != null) { %>
        <div class="product-card">
            <!-- Ảnh -->
            <div class="product-image">
                <img src="<%= request.getContextPath() %>/images/<%= shoe.imageUrl %>" 
                     alt="<%= shoe.name %>">
            </div>

            <!-- Thông tin -->
            <div class="product-info">
                <h1 class="product-title"><%= shoe.name %></h1>
                <p><strong>Hãng:</strong> <%= shoe.brand %></p>
                <p><strong>Danh mục:</strong> <%= shoe.category %></p>
                <p><strong>Giá:</strong> <span class="price"><%= shoe.price %> VND</span></p>
                <p><strong>Mô tả:</strong> <%= shoe.description %></p>

                <!-- SIZE & COLOR -->
                <% if (shoe.variants != null && !shoe.variants.isEmpty()) { %>
                    <div class="variant-group">
                        <p><strong>Size:</strong></p>
                        <div class="size-options">
                            <%
                                java.util.Set<String> displayedSizes = new java.util.HashSet<>();
                                int sizeIndex = 0;
                                for (presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v : shoe.variants) {
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
                                for (presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v : shoe.variants) {
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
                        data-productid="<%= shoe.id %>"        
                        data-productprice="<%= shoe.price %>">
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
    // === XỬ LÝ SIZE ===
    document.querySelectorAll('.size-btn').forEach(label => {
        const input = label.querySelector('input');
        
        label.addEventListener('click', () => {
            if (input && input.disabled) return;
            document.querySelectorAll('.size-btn').forEach(l => l.classList.remove('selected'));
            label.classList.add('selected');
        });

        // Nếu đã được chọn trước (load trang)
        if (input && input.checked) {
            label.classList.add('selected');
        }
    });

    // === XỬ LÝ MÀU ===
    document.querySelectorAll('.color-text-btn').forEach(label => {
        const input = label.querySelector('input');
        
        label.addEventListener('click', () => {
            document.querySelectorAll('.color-text-btn').forEach(l => l.classList.remove('selected'));
            label.classList.add('selected');
        });

        if (input && input.checked) {
            label.classList.add('selected');
        }
    });

    // === TỰ ĐỘNG CHỌN ĐẦU TIÊN (nếu chưa chọn) ===
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

    // === CỘNG/TRỪ SỐ LƯỢNG ===
    document.querySelector('.qty-btn.plus').onclick = () => {
        const q = document.getElementById('quantity');
        q.value = parseInt(q.value) + 1;
    };
    document.querySelector('.qty-btn.minus').onclick = () => {
        const q = document.getElementById('quantity');
        if (q.value > 1) q.value = parseInt(q.value) - 1;
    };

    // === THÊM VÀO GIỎ HÀNG ===
    const btn = document.getElementById('addToCartBtn');
    if (btn) {
        const productId = parseInt(btn.dataset.productid);
        const productPrice = parseFloat(btn.dataset.productprice);

        btn.addEventListener('click', function() {
            const sizeRadio = document.querySelector('input[name="size"]:checked');
            const colorRadio = document.querySelector('input[name="color"]:checked');
            const quantity = document.getElementById('quantity').value;

            if (!sizeRadio) { alert('Vui lòng chọn size!'); return; }
            if (!colorRadio) { alert('Vui lòng chọn màu!'); return; }
            if (!quantity || quantity < 1) { alert('Số lượng phải ≥ 1'); return; }

            const data = new URLSearchParams({
                productId: productId,
                size: sizeRadio.value,
                color: colorRadio.value,
                quantity: quantity,
                price: productPrice
            });

            fetch('<%= request.getContextPath() %>/AddToCart', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: data
            })
            .then(res => res.json())
            .then(result => {
                if (result.success) {
                    alert(result.message);
                    const cartCount = document.getElementById('cartCount');
                    if (cartCount) cartCount.textContent = result.totalItems;
                } else {
                    alert('Lỗi: ' + result.message);
                }
            })
            .catch(err => {
                console.error(err);
                alert('Lỗi kết nối server!');
            });
        });
    }
});
</script>
</body>
</html>