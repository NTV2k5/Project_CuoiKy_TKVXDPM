<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Web Bán Giày Đẹp</title>

  <!-- Minimal, self-contained CSS để dễ thử nghiệm -->
  <style>
    :root{
      --accent:#ff6b6b;
      --dark:#222;
      --muted:#777;
      --card-bg:#fff;
      --page-bg:#f5f7fb;
      --max-width:1200px;
      --radius:12px;
      font-family: "Helvetica Neue", Arial, sans-serif;
    }

    *{box-sizing:border-box}
    body{
      margin:0;
      background:var(--page-bg);
      color:var(--dark);
      -webkit-font-smoothing:antialiased;
      -moz-osx-font-smoothing:grayscale;
    }

    /* Container */
    .container{
      width:96%;
      max-width:var(--max-width);
      margin:24px auto;
    }

    /* Header / Navbar */
    header.site-header{
      background:#fff;
      padding:16px 0;
      border-radius: var(--radius);
      box-shadow: 0 6px 20px rgba(12,20,40,0.06);
    }
    .nav{
      display:flex;
      gap:16px;
      align-items:center;
      justify-content:space-between;
    }
    .brand{
      display:flex;
      gap:12px;
      align-items:center;
    }
    .brand .logo{
      width:46px;height:46px;border-radius:10px;background:linear-gradient(135deg,var(--accent),#ff9a9e);
      display:flex;align-items:center;justify-content:center;color:#fff;font-weight:700;
      box-shadow:0 4px 12px rgba(255,107,107,0.18);
    }
    .brand h1{font-size:18px;margin:0}
    .search{
      flex:1;margin:0 20px;
      display:flex;
    }
    .search input{
      flex:1;padding:10px 14px;border-radius:10px 0 0 10px;border:1px solid #e3e7ee;
      outline:none;
      font-size:14px;
    }
    .search button{
      padding:10px 14px;border-radius:0 10px 10px 0;border:1px solid #e3e7ee;background:var(--accent);color:#fff;font-weight:600;
      cursor:pointer;
    }
    .nav-actions{display:flex;gap:12px;align-items:center}
    .btn{
      background:#fff;border:1px solid #e6e9ef;padding:8px 12px;border-radius:10px;cursor:pointer;
    }

    /* Hero */
    .hero{
      margin-top:18px;
      display:grid;
      grid-template-columns:1fr 420px;
      gap:18px;
      align-items:center;
    }
    .hero-card{
      background: linear-gradient(180deg,#fff, #fbfdff);
      padding:28px;border-radius:14px;box-shadow:0 10px 30px rgba(12,20,40,0.05);
    }
    .hero-card h2{margin:0 0 8px;font-size:28px}
    .hero-card p{color:var(--muted);margin:0 0 16px}
    .cta{display:inline-block;padding:10px 18px;border-radius:10px;background:var(--accent);color:#fff;font-weight:700;text-decoration:none}

    .promo{
      background: linear-gradient(90deg,#ffecd2,#fcb69f);
      border-radius:14px;padding:18px;display:flex;gap:12px;align-items:center;
    }
    .promo img{width:120px;height:80px;object-fit:cover;border-radius:8px}

    /* Product grid */
    .section{
      margin-top:22px;
    }
    .section h3{margin:0 0 12px}
    .grid{
      display:grid;
      grid-template-columns:repeat(4,1fr);
      gap:16px;
    }
    .card{
      background:var(--card-bg);padding:12px;border-radius:12px;box-shadow:0 6px 18px rgba(12,20,40,0.04);
      display:flex;flex-direction:column;gap:8px;
    }
    .thumb{width:100%;height:180px;border-radius:8px;overflow:hidden;background:#f2f4f8;display:flex;align-items:center;justify-content:center}
    .thumb img{width:100%;height:100%;object-fit:cover}
    .product-name{font-weight:700;font-size:15px}
    .price-row{display:flex;justify-content:space-between;align-items:center}
    .price{color:var(--accent);font-weight:800}
    .muted{color:var(--muted);font-size:13px}
    .card .actions{display:flex;gap:8px;margin-top:auto}
    .outline{background:transparent;border:1px solid #e6e9ef;padding:8px 10px;border-radius:10px;cursor:pointer}
    /* nút xem danh sách */
  .btn-view-all 
  {
    display: inline-block;
    background-color: #007bff;
    color: white;
    font-weight: 600;
    padding: 12px 24px;
    border-radius: 8px;
    text-decoration: none;
    transition: background-color 0.3s ease;
  }

  .btn-view-all:hover 
  {
    background-color: #0056b3;
  }

    /* Footer */
    footer{
      margin-top:28px;padding:18px 0;color:var(--muted);
      text-align:center;font-size:14px;
    }

    /* Responsive */
    @media (max-width:1000px){
      .hero{grid-template-columns:1fr}
      .grid{grid-template-columns:repeat(2,1fr)}
    }
    @media (max-width:600px){
      .nav{flex-direction:column;align-items:flex-start;gap:12px}
      .search{order:2;width:100%}
      .grid{grid-template-columns:repeat(1,1fr)}
      .thumb{height:220px}
      .brand h1{font-size:16px}
    }
  </style>
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
          <input type="search" placeholder="Tìm giày, size, thương hiệu..." aria-label="Tìm sản phẩm" />
          <button>🔍</button>
        </div>

        <div class="nav-actions">
            <a class="btn" href="login.jsp">Đăng nhập</a>
            <a class="btn" href="register.jsp">Đăng ký</a>
            <button class="btn">Giỏ (0)</button>
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
