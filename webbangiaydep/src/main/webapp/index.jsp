<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <link rel="stylesheet" href="index.css">
  <title>Web Bán Giày Đẹp</title>
</head>
<body>
  <div class="container">
    <!-- Header -->
    <header class="site-header">
      <div class="nav">
        <div class="brand">
          <div class="logo">SG</div>
          <div>
            <h1>Shop Giày Đẹp</h1>
            <div class="muted" style="font-size:12px">Giày đẹp • Giá tốt • Giao nhanh</div>
          </div>
        </div>

        <div class="search" role="search">
          <form action="${pageContext.request.contextPath}/searchShoeList" method="get" style="display:flex; width:100%;">
            <input type="search" name="keyword" placeholder="Tìm giày, size, thương hiệu..." aria-label="Tìm sản phẩm" />
            <button type="submit">🔍</button>
          </form>
        </div>

        <div class="nav-actions">
            <a class="btn" href="login.jsp">Đăng nhập</a>
            <a class="btn" href="register.jsp">Đăng ký</a>
            <a class="btn" href="${pageContext.request.contextPath}/viewShoeCart">Giỏ (0)</a>
        </div>
      </div>
    </header>

    <!-- Hero -->
    <div class="hero">
      <div class="hero-card">
        <h2>BST Mùa Hè — Giảm giá đến 30%</h2>
        <p>Khám phá các mẫu giày thể thao & lifestyle mới nhất. Miễn phí giao hàng cho đơn trên 1.000.000₫</p>

        <div style="display:flex;gap:12px;margin-top:12px">
          <a class="cta" href="#">Xem ngay</a>
          <a class="outline" href="#">Bộ sưu tập nam</a>
          <a class="outline" href="#">Bộ sưu tập nữ</a>
        </div>

        <div style="margin-top:18px" class="promo">
          <img src="https://via.placeholder.com/240x160.png?text=Promo" alt="promo"/>
          <div>
            <div style="font-weight:700">Giảm thêm 10% cho đơn hàng đầu tiên</div>
            <div class="muted">Dùng mã: NEW10 • Hạn đến: 30/11/2025</div>
          </div>
        </div>
      </div>

      <aside>
        <div style="background:#fff;padding:18px;border-radius:12px;box-shadow:0 10px 30px rgba(12,20,40,0.04)">
          <h4 style="margin:0 0 10px">Danh mục nổi bật</h4>
          <ul style="list-style:none;padding:0;margin:0;display:flex;flex-direction:column;gap:8px">
            <li><a href="#" class="muted">Giày thể thao</a></li>
            <li><a href="#" class="muted">Giày đi học</a></li>
            <li><a href="#" class="muted">Sandals & Dép</a></li>
            <li><a href="#" class="muted">Giày da</a></li>
            <li><a href="#" class="muted">Sale 50%</a></li>
          </ul>
        </div>
      </aside>
    </div>

    <!-- Products -->
    <section class="section">
      <h3>Sản phẩm nổi bật</h3>
      <div class="grid">
        <!-- Card 1 -->
        <div class="card">
          <div class="thumb">
            <img src="https://via.placeholder.com/600x400.png?text=Sneaker+1" alt="Sneaker 1"/>
          </div>
          <div class="product-name">Sneaker UltraRun</div>
          <div class="muted">Nike • Size: 39-44</div>
          <div class="price-row">
            <div class="price">1.250.000₫</div>
            <div class="muted">Còn 12</div>
          </div>
          <div class="actions">
            <button class="outline">Chi tiết</button>
            <button class="cta" style="background:var(--accent);border:none">Thêm vào giỏ</button>
          </div>
        </div>

        <!-- Card 2 -->
        <div class="card">
          <div class="thumb">
            <img src="https://via.placeholder.com/600x400.png?text=Sandal+2" alt="Sandal"/>
          </div>
          <div class="product-name">Sandal Summer</div>
          <div class="muted">Local Brand • Size: 36-41</div>
          <div class="price-row">
            <div class="price">320.000₫</div>
            <div class="muted">Còn 30</div>
          </div>
          <div class="actions">
            <button class="outline">Chi tiết</button>
            <button class="cta" style="background:var(--accent);border:none">Thêm vào giỏ</button>
          </div>
        </div>

        <!-- Card 3 -->
        <div class="card">
          <div class="thumb">
            <img src="https://via.placeholder.com/600x400.png?text=Gi%E1%BA%A3y+Da+3" alt="Giày da"/>
          </div>
          <div class="product-name">Giày Da Classic</div>
          <div class="muted">BrandX • Size: 38-43</div>
          <div class="price-row">
            <div class="price">899.000₫</div>
            <div class="muted">Còn 7</div>
          </div>
          <div class="actions">
            <button class="outline">Chi tiết</button>
            <button class="cta" style="background:var(--accent);border:none">Thêm vào giỏ</button>
          </div>
        </div>

        <!-- Card 4 -->
        <div class="card">
          <div class="thumb">
            <img src="https://via.placeholder.com/600x400.png?text=Running+4" alt="Running"/>
          </div>
          <div class="product-name">Running Pro 2025</div>
          <div class="muted">TopBrand • Size: 39-45</div>
          <div class="price-row">
            <div class="price">1.590.000₫</div>
            <div class="muted">Còn 5</div>
          </div>
          <div class="actions">
            <button class="outline">Chi tiết</button>
            <button class="cta" style="background:var(--accent);border:none">Thêm vào giỏ</button>
          </div>
        </div>
      </div>
      <!-- Nút xem danh sách giày -->
    <div style="text-align:center; margin-top:24px;">
      <a href="${pageContext.request.contextPath}/shoes" 
         class="btn-view-all">
         Xem danh sách giày
      </a>
    </div>
    </section>

    <!-- Footer -->
    <footer>
      © 2025 Shop Giày Đẹp — Địa chỉ: Số 1, Đường Demo, TP. HCM • Hotline: 0909 000 000
    </footer>
  </div>
</body>
</html>
