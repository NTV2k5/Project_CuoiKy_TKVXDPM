-- Sửa lại database: Dùng plain text password, thêm role GUEST, mật khẩu khác nhau cho mỗi user
-- Chạy script này để DROP và CREATE lại (backup DB trước nếu cần)
DROP DATABASE IF EXISTS shoesdb;
CREATE DATABASE shoesdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shoesdb;

-- ===========================================================
-- BẢNG ROLE (thêm GUEST)
-- ===========================================================
CREATE TABLE role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);
INSERT INTO role (code, description) VALUES
('ADMIN', 'Quản trị hệ thống'),
('CUSTOMER', 'Người dùng có tài khoản'),
('GUEST', 'Người dùng không có tài khoản');

-- ===========================================================
-- BẢNG NGƯỜI DÙNG (11 users: 1 admin + 10 customers, password plain text khác nhau)
-- ===========================================================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Giờ là plain text
    role_id INT NOT NULL DEFAULT 2,
    full_name VARCHAR(200),
    phone VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active TINYINT(1) DEFAULT 1,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES role(id)
);
INSERT INTO users (email, password, role_id, full_name, phone) VALUES
('admin@example.com', 'admin123', 1, 'Admin Hệ Thống', '0900000000'),  -- Password: admin123
('user1@example.com', 'pass1', 2, 'Người Dùng 1', '0911000001'),       -- Password: pass1
('user2@example.com', 'pass2', 2, 'Người Dùng 2', '0911000002'),       -- Password: pass2
('user3@example.com', 'pass3', 2, 'Người Dùng 3', '0911000003'),       -- Password: pass3
('user4@example.com', 'pass4', 2, 'Người Dùng 4', '0911000004'),       -- Password: pass4
('user5@example.com', 'pass5', 2, 'Người Dùng 5', '0911000005'),       -- Password: pass5
('user6@example.com', 'pass6', 2, 'Người Dùng 6', '0911000006'),       -- Password: pass6
('user7@example.com', 'pass7', 2, 'Người Dùng 7', '0911000007'),       -- Password: pass7
('user8@example.com', 'pass8', 2, 'Người Dùng 8', '0911000008'),       -- Password: pass8
('user9@example.com', 'pass9', 2, 'Người Dùng 9', '0911000009'),       -- Password: pass9
('user10@example.com', 'pass10', 2, 'Người Dùng 10', '0911000010');    -- Password: pass10

-- ===========================================================
-- BẢNG SIZE (tách riêng)
-- ===========================================================
CREATE TABLE size (
    id INT AUTO_INCREMENT PRIMARY KEY,
    value VARCHAR(10) NOT NULL UNIQUE,
    sort_order INT DEFAULT 0
);
INSERT INTO size (value, sort_order) VALUES
('38', 38), ('39', 39), ('40', 40), ('41', 41), ('42', 42), ('43', 43), ('44', 44);

-- ===========================================================
-- BẢNG COLOR (tách riêng)
-- ===========================================================
CREATE TABLE color (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    hex_code VARCHAR(7) DEFAULT NULL
);
INSERT INTO color (name, hex_code) VALUES
('Black', '#000000'),
('White', '#FFFFFF'),
('Gray', '#808080'),
('White/Black', '#FFFFFF'),
('White/Red', '#FF0000'),
('Blue', '#0000FF'),
('Brown', '#8B4513'),
('Green', '#008000'),
('Navy', '#000080'),
('Beige', '#F5F5DC');

-- ===========================================================
-- DANH MỤC SẢN PHẨM (3 cha + 7 con = 10 categories)
-- ===========================================================
CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    parent_id INT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE SET NULL
);
-- CHA
INSERT INTO category (code, name, description) VALUES
('MEN', 'Nam', 'Danh mục giày nam'),
('WOMEN', 'Nữ', 'Danh mục giày nữ'),
('UNISEX', 'Unisex', 'Danh mục giày unisex');
-- LẤY ID CHA
SET @men_id = (SELECT id FROM category WHERE code = 'MEN');
SET @women_id = (SELECT id FROM category WHERE code = 'WOMEN');
SET @unisex_id = (SELECT id FROM category WHERE code = 'UNISEX');
-- CON
INSERT INTO category (code, name, description, parent_id) VALUES
('RUNNING', 'Giày chạy bộ', 'Dành cho vận động viên và người yêu thể thao', @men_id),
('SNEAKER', 'Giày sneaker', 'Dòng giày thời trang trẻ trung', @unisex_id),
('BOOT', 'Giày boot', 'Giày boot da cao cổ', @women_id),
('SANDAL', 'Sandal', 'Giày dép sandal cho mùa hè', @unisex_id),
('FORMAL', 'Giày công sở', 'Phù hợp môi trường văn phòng', @men_id),
('FLAT', 'Giày búp bê', 'Giày nữ đế thấp thoải mái', @women_id),
('CASUAL', 'Giày casual', 'Giày hàng ngày thoải mái', @men_id);

-- ===========================================================
-- SẢN PHẨM (16 sản phẩm - Bỏ stock/price vì chuyển sang variant)
-- ===========================================================
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    short_description VARCHAR(512),
    description TEXT,
    imageUrl VARCHAR(1000),
    brand VARCHAR(100),
    category_id INT,
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);
INSERT INTO product (sku, name, short_description, description, imageUrl, brand, category_id) VALUES
('SKU001', 'Nike Air Force 1', 'Giày sneaker nam kinh điển', 'Thiết kế trắng đen huyền thoại', 'sku001.jpg', 'Nike', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU002', 'Adidas Superstar', 'Giày sneaker trắng biểu tượng', 'Shell toe nổi bật', 'sku002.jpg', 'Adidas', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU003', 'Vans Old Skool', 'Giày skate unisex', 'Dòng giày cổ điển', 'sku003.jpg', 'Vans', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU004', 'Converse Chuck Taylor', 'Giày cao cổ unisex', 'All Star huyền thoại', 'sku004.jpg', 'Converse', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU005', 'New Balance 574', 'Giày casual nam', 'Êm ái hàng ngày', 'sku005.jpg', 'New Balance', (SELECT id FROM category WHERE code='CASUAL')),
('SKU006', 'Puma RS-X', 'Giày thể thao chunky', 'Thiết kế hiện đại', 'sku006.jpg', 'Puma', (SELECT id FROM category WHERE code='RUNNING')),
('SKU007', 'Bitis Hunter Street', 'Sneaker Việt Nam', 'Bền, êm, giá hợp lý', 'sku007.jpg', 'Bitis', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU008', 'Dr.Martens 1460', 'Giày boot da cổ điển', '8 lỗ huyền thoại', 'sku008.jpg', 'Dr.Martens', (SELECT id FROM category WHERE code='BOOT')),
('SKU009', 'Ecco Helsinki', 'Giày tây nam', 'Da thật cao cấp', 'sku009.jpg', 'Ecco', (SELECT id FROM category WHERE code='FORMAL')),
('SKU010', 'Crocs Classic Clog', 'Dép unisex', 'Thoải mái, nhẹ', 'sku010.jpg', 'Crocs', (SELECT id FROM category WHERE code='SANDAL')),
('SKU011', 'Nike Revolution 6', 'Giày chạy bộ nhẹ', 'Bền bỉ, êm ái', 'sku011.jpg', 'Nike', (SELECT id FROM category WHERE code='RUNNING')),
('SKU012', 'Adidas Stan Smith', 'Sneaker đơn giản', 'Dễ phối đồ', 'sku012.jpg', 'Adidas', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU013', 'Timberland Premium Boot', 'Boot chống nước', 'Mùa đông', 'sku013.jpg', 'Timberland', (SELECT id FROM category WHERE code='BOOT')),
('SKU014', 'Gucci Ace Sneaker', 'Sneaker cao cấp', 'Da thật, sang trọng', 'sku014.jpg', 'Gucci', (SELECT id FROM category WHERE code='SNEAKER')),
('SKU015', 'Toms Classic Flat', 'Giày slip-on nữ', 'Thoải mái', 'sku015.jpg', 'Toms', (SELECT id FROM category WHERE code='FLAT')),
('SKU016', 'Nike Air Max 270', 'Sneaker khí nén', 'Êm ái, hiện đại', 'sku016.jpg', 'Nike', (SELECT id FROM category WHERE code='RUNNING'));

-- ===========================================================
-- HÌNH ẢNH SẢN PHẨM
-- ===========================================================
CREATE TABLE product_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    url VARCHAR(1000) NOT NULL,
    alt_text VARCHAR(255),
    is_primary TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prodimg_prod FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
INSERT INTO product_image (product_id, url, alt_text, is_primary) VALUES
(1, 'sku001-1.jpg', 'Nike Air Force 1 chính', 1),
(1, 'sku001-2.jpg', 'Nike Air Force 1 phụ', 0),
(2, 'sku002-1.jpg', 'Adidas Superstar chính', 1),
(3, 'sku003-1.jpg', 'Vans Old Skool chính', 1),
(4, 'sku004-1.jpg', 'Converse Chuck Taylor chính', 1),
(5, 'sku005-1.jpg', 'New Balance 574 chính', 1),
(6, 'sku006-1.jpg', 'Puma RS-X chính', 1),
(7, 'sku007-1.jpg', 'Bitis Hunter chính', 1),
(8, 'sku008-1.jpg', 'Dr.Martens chính', 1),
(9, 'sku009-1.jpg', 'Ecco Helsinki chính', 1),
(10, 'sku010-1.jpg', 'Crocs Clog chính', 1),
(11, 'sku011-1.jpg', 'Nike Revolution chính', 1),
(12, 'sku012-1.jpg', 'Adidas Stan Smith chính', 1),
(13, 'sku013-1.jpg', 'Timberland Boot chính', 1),
(14, 'sku014-1.jpg', 'Gucci Ace chính', 1),
(15, 'sku015-1.jpg', 'Toms Flat chính', 1),
(16, 'sku016-1.jpg', 'Nike Air Max chính', 1);

-- ===========================================================
-- BIẾN THỂ SẢN PHẨM (dùng size_id, color_id, thêm price cho variant)
-- ===========================================================
CREATE TABLE product_variant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    sku_variant VARCHAR(100) UNIQUE,
    size_id INT NOT NULL,
    color_id INT NOT NULL,
    price DOUBLE NOT NULL,  -- Chuyển price từ product sang variant
    extra_price DOUBLE DEFAULT 0,
    stock INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_variant_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    CONSTRAINT fk_variant_size FOREIGN KEY (size_id) REFERENCES size(id),
    CONSTRAINT fk_variant_color FOREIGN KEY (color_id) REFERENCES color(id),
    UNIQUE KEY uq_product_size_color (product_id, size_id, color_id)
);
-- Giả sử price từ INSERT cũ, gán cho variant (stock từ script)
INSERT INTO product_variant (product_id, sku_variant, size_id, color_id, price, stock) VALUES
(1, 'SKU001-40-BLACK', (SELECT id FROM size WHERE value='40'), (SELECT id FROM color WHERE name='Black'), 2590000, 50),
(1, 'SKU001-41-BLACK', (SELECT id FROM size WHERE value='41'), (SELECT id FROM color WHERE name='Black'), 2590000, 45),
(2, 'SKU002-41-WHITE', (SELECT id FROM size WHERE value='41'), (SELECT id FROM color WHERE name='White'), 2390000, 20),
(2, 'SKU002-42-WHITE', (SELECT id FROM size WHERE value='42'), (SELECT id FROM color WHERE name='White'), 2390000, 15),
(3, 'SKU003-42-GRAY', (SELECT id FROM size WHERE value='42'), (SELECT id FROM color WHERE name='Gray'), 1790000, 30),
(3, 'SKU003-43-GRAY', (SELECT id FROM size WHERE value='43'), (SELECT id FROM color WHERE name='Gray'), 1790000, 25),
(4, 'SKU004-39-BLACK', (SELECT id FROM size WHERE value='39'), (SELECT id FROM color WHERE name='Black'), 1990000, 25),
(4, 'SKU004-40-BLACK', (SELECT id FROM size WHERE value='40'), (SELECT id FROM color WHERE name='Black'), 1990000, 20),
(5, 'SKU005-43-BROWN', (SELECT id FROM size WHERE value='43'), (SELECT id FROM color WHERE name='Brown'), 2490000, 35),
(6, 'SKU006-40-WHITE', (SELECT id FROM size WHERE value='40'), (SELECT id FROM color WHERE name='White'), 2290000, 40),
(7, 'SKU007-40-GRAY', (SELECT id FROM size WHERE value='40'), (SELECT id FROM color WHERE name='Gray'), 990000, 20),
(7, 'SKU007-41-GRAY', (SELECT id FROM size WHERE value='41'), (SELECT id FROM color WHERE name='Gray'), 990000, 20),
(8, 'SKU008-42-WHITE', (SELECT id FROM size WHERE value='42'), (SELECT id FROM color WHERE name='White'), 3690000, 15),
(9, 'SKU009-43-BROWN', (SELECT id FROM size WHERE value='43'), (SELECT id FROM color WHERE name='Brown'), 4990000, 10),
(10, 'SKU010-42-BLACK', (SELECT id FROM size WHERE value='42'), (SELECT id FROM color WHERE name='Black'), 890000, 25),
(11, 'SKU011-40-NAVY', (SELECT id FROM size WHERE value='40'), (SELECT id FROM color WHERE name='Navy'), 1890000, 30),
(12, 'SKU012-44-BLACK', (SELECT id FROM size WHERE value='44'), (SELECT id FROM color WHERE name='Black'), 2190000, 8),
(13, 'SKU013-41-WHITE', (SELECT id FROM size WHERE value='41'), (SELECT id FROM color WHERE name='White'), 3290000, 4),
(14, 'SKU014-42-BROWN', (SELECT id FROM size WHERE value='42'), (SELECT id FROM color WHERE name='Brown'), 14500000, 10),
(15, 'SKU015-38-BEIGE', (SELECT id FROM size WHERE value='38'), (SELECT id FROM color WHERE name='Beige'), 1190000, 12),
(16, 'SKU016-42-WHITE', (SELECT id FROM size WHERE value='42'), (SELECT id FROM color WHERE name='White'), 3390000, 20);

-- ===========================================================
-- ĐỊA CHỈ KHÁCH HÀNG (10 addresses)
-- ===========================================================
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    full_name VARCHAR(200),
    phone VARCHAR(30),
    city VARCHAR(100),
    province VARCHAR(150),
    district VARCHAR(150),
    ward VARCHAR(150),
    street VARCHAR(255),
    note VARCHAR(512),
    is_default TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
INSERT INTO address (user_id, full_name, phone, city, province, district, street, is_default) VALUES
(2, 'Người Dùng 2', '0901234567', 'TP.HCM', 'TP.HCM', 'Quận 1', '123 Lê Lợi', 1),
(3, 'Người Dùng 3', '0912345678', 'Hà Nội', 'Hà Nội', 'Hoàn Kiếm', '45 Hai Bà Trưng', 1),
(4, 'Người Dùng 4', '0934567890', 'TP.HCM', 'TP.HCM', 'Quận 1', '67 Nguyễn Huệ', 1),
(5, 'Người Dùng 5', '0945678901', 'Đà Nẵng', 'Đà Nẵng', 'Hải Châu', '12 Phan Chu Trinh', 1),
(6, 'Người Dùng 6', '0956789012', 'Huế', 'Thừa Thiên Huế', 'Phú Nhuận', '89 Lý Thường Kiệt', 1),
(7, 'Người Dùng 7', '0967890123', 'TP.HCM', 'TP.HCM', 'Quận 3', '23 Võ Văn Tần', 1),
(8, 'Người Dùng 8', '0978901234', 'Hà Nội', 'Hà Nội', 'Ba Đình', '56 Điện Biên Phủ', 1),
(9, 'Người Dùng 9', '0989012345', 'Cần Thơ', 'Cần Thơ', 'Ninh Kiều', '78 Nguyễn Văn Cừ', 1),
(10, 'Người Dùng 10', '0990123456', 'Đà Nẵng', 'Đà Nẵng', 'Thanh Khê', '90 Lê Duẩn', 1),
(1, 'Admin Hệ Thống', '0901122334', 'TP.HCM', 'TP.HCM', 'Quận 5', '101 Trần Hưng Đạo', 1);

-- ===========================================================
-- ĐƠN HÀNG + LỊCH SỬ TRẠNG THÁI
-- ===========================================================
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    address_id BIGINT,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE DEFAULT 0,
    status ENUM('PENDING','PAID','PROCESSING','SHIPPED','COMPLETED','CANCELLED') DEFAULT 'PENDING',
    payment_method VARCHAR(100),
    note VARCHAR(512),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE SET NULL
);
CREATE TABLE order_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status ENUM('PENDING','PAID','PROCESSING','SHIPPED','COMPLETED','CANCELLED') NOT NULL,
    note VARCHAR(512),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by BIGINT,
    CONSTRAINT fk_hist_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_hist_by FOREIGN KEY (changed_by) REFERENCES users(id) ON DELETE SET NULL
);
INSERT INTO orders (user_id, address_id, total, status, payment_method) VALUES
(2, (SELECT id FROM address WHERE user_id=2), 0, 'PAID', 'COD'),
(3, (SELECT id FROM address WHERE user_id=3), 0, 'SHIPPED', 'CARD'),
(4, (SELECT id FROM address WHERE user_id=4), 0, 'PENDING', 'COD'),
(5, (SELECT id FROM address WHERE user_id=5), 0, 'PAID', 'CARD'),
(6, (SELECT id FROM address WHERE user_id=6), 0, 'PROCESSING', 'COD'),
(7, (SELECT id FROM address WHERE user_id=7), 0, 'PAID', 'CARD'),
(8, (SELECT id FROM address WHERE user_id=8), 0, 'PAID', 'COD'),
(9, (SELECT id FROM address WHERE user_id=9), 0, 'PAID', 'CARD'),
(10, (SELECT id FROM address WHERE user_id=10), 0, 'PENDING', 'COD'),
(1, (SELECT id FROM address WHERE user_id=1), 0, 'COMPLETED', 'CARD');

-- ===========================================================
-- CHI TIẾT ĐƠN HÀNG (11 items - sửa variant_id cho khớp)
-- ===========================================================
CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    variant_id BIGINT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,  -- Giữ price ở đây cho order
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_orderitem_variant FOREIGN KEY (variant_id) REFERENCES product_variant(id) ON DELETE SET NULL
);
INSERT INTO order_item (order_id, product_id, variant_id, quantity, price) VALUES
(1, 1, 1, 1, 2590000),  -- SKU001-40-BLACK
(2, 4, NULL, 1, 1990000),
(2, 6, NULL, 1, 2290000),
(3, 11, 16, 1, 1890000),  -- SKU011-40-NAVY
(4, 12, 17, 1, 2190000),  -- SKU012-44-BLACK
(5, 13, 18, 1, 3290000),  -- SKU013-41-WHITE
(6, 10, 15, 2, 890000),   -- SKU010-42-BLACK
(7, 8, 13, 1, 3690000),   -- SKU008-42-WHITE
(8, 14, 19, 1, 14500000), -- SKU014-42-BROWN
(9, 15, 20, 1, 1190000),  -- SKU015-38-BEIGE
(10, 16, NULL, 1, 3390000);

-- ===========================================================
-- CẬP NHẬT TỔNG TIỀN ĐƠN HÀNG (TRIGGER)
-- ===========================================================
DELIMITER $$
CREATE TRIGGER trg_update_order_total_insert AFTER INSERT ON order_item
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total = (SELECT SUM(quantity * price) FROM order_item WHERE order_id = NEW.order_id)
    WHERE id = NEW.order_id;
END$$
CREATE TRIGGER trg_update_order_total_update AFTER UPDATE ON order_item
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total = (SELECT SUM(quantity * price) FROM order_item WHERE order_id = NEW.order_id)
    WHERE id = NEW.order_id;
    UPDATE orders
    SET total = (SELECT SUM(quantity * price) FROM order_item WHERE order_id = OLD.order_id)
    WHERE id = OLD.order_id;
END$$
CREATE TRIGGER trg_update_order_total_delete AFTER DELETE ON order_item
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total = (SELECT IFNULL(SUM(quantity * price), 0) FROM order_item WHERE order_id = OLD.order_id)
    WHERE id = OLD.order_id;
END$$
DELIMITER ;

-- ===========================================================
-- GIỎ HÀNG
-- ===========================================================
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NULL,
    session_id VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uq_user_session (user_id, session_id)
);
CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    variant_id BIGINT NULL,
    quantity INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cartitem_cart FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    CONSTRAINT fk_cartitem_prod FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_cartitem_variant FOREIGN KEY (variant_id) REFERENCES product_variant(id) ON DELETE SET NULL,
    UNIQUE KEY uq_cart_prod_variant (cart_id, product_id, variant_id)
);
INSERT INTO cart (user_id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);

-- ===========================================================
-- THANH TOÁN
-- ===========================================================
CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    method VARCHAR(100),
    provider_transaction_id VARCHAR(255),
    status ENUM('PENDING','SUCCESS','FAILED') DEFAULT 'PENDING',
    paid_at DATETIME NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
INSERT INTO payment (order_id, amount, method, provider_transaction_id, status, paid_at) VALUES
(1, 2590000, 'COD', 'TXN001', 'SUCCESS', NOW()),
(2, 4280000, 'CARD', 'TXN002', 'SUCCESS', NOW()),
(3, 1890000, 'COD', 'TXN003', 'PENDING', NULL),
(4, 2190000, 'CARD', 'TXN004', 'SUCCESS', NOW()),
(5, 3290000, 'COD', 'TXN005', 'SUCCESS', NOW()),
(6, 1780000, 'CARD', 'TXN006', 'SUCCESS', NOW()),
(7, 3690000, 'COD', 'TXN007', 'SUCCESS', NOW()),
(8, 14500000, 'CARD', 'TXN008', 'SUCCESS', NOW()),
(9, 1190000, 'COD', 'TXN009', 'PENDING', NULL),
(10, 3390000, 'CARD', 'TXN010', 'SUCCESS', NOW());

-- ===========================================================
-- INDEX & VIEW (cập nhật view cho price từ variant)
-- ===========================================================
CREATE INDEX idx_product_name ON product(name);
CREATE INDEX idx_product_sku ON product(sku);
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orderitem_order ON order_item(order_id);
CREATE OR REPLACE VIEW view_product_sales AS
SELECT p.id, p.name, IFNULL(SUM(oi.quantity), 0) AS total_sold, IFNULL(SUM(oi.price * oi.quantity), 0) AS revenue
FROM product p LEFT JOIN order_item oi ON p.id = oi.product_id
GROUP BY p.id;
CREATE OR REPLACE VIEW view_order_details AS
SELECT o.id, u.email, o.status, p.name, oi.quantity, oi.price, (oi.quantity * oi.price) AS subtotal
FROM orders o
JOIN users u ON o.user_id = u.id
JOIN order_item oi ON o.id = oi.order_id
JOIN product p ON oi.product_id = p.id;

-- ===========================================================
-- QUERY TEST SẢN PHẨM (cập nhật price/stock từ variant)
-- ===========================================================
SELECT
    p.id,
    p.name,
    pv.price,
    p.imageUrl,
    p.brand,
    c.name AS category_name,
    SUM(pv.stock) AS stock_quantity,
    p.is_active AS available
FROM product p
JOIN category c ON p.category_id = c.id
LEFT JOIN product_variant pv ON p.id = pv.product_id
WHERE p.is_active = 1
GROUP BY p.id, p.name, pv.price, p.imageUrl, p.brand, c.name, p.is_active
ORDER BY p.id;

-- ===========================================================
-- HOÀN TẤT
-- ===========================================================