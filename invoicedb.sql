-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS InvoiceDB;
USE InvoiceDB;

-- 📌 Bảng danh mục món ăn
CREATE TABLE IF NOT EXISTS Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng Categories (sử dụng INSERT IGNORE để tránh trùng lặp)
INSERT IGNORE INTO Categories (Name) VALUES 
('Danh mục 1'),
('Danh mục 2');

-- 📌 Bảng thực đơn
CREATE TABLE IF NOT EXISTS MenuItems (
    ItemID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Description TEXT,
    CategoryID INT NOT NULL,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng MenuItems
INSERT IGNORE INTO MenuItems (Name, Price, Description, CategoryID) VALUES 
('Món 1', 75.00, 'Mô tả món 1', 1),  -- ItemID 1
('Món 2', 150.00, 'Mô tả món 2', 1), -- ItemID 2
('Món 3', 25.00, 'Mô tả món 3', 1);   -- ItemID 3

-- 📌 Bảng đơn hàng
CREATE TABLE IF NOT EXISTS Orders (
    OrderID INT PRIMARY KEY AUTO_INCREMENT,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status ENUM('PENDING', 'COMPLETED', 'CANCELLED', 'PAID') DEFAULT 'PENDING'  -- Trạng thái đơn hàng
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng Orders
INSERT IGNORE INTO Orders (TotalAmount, Status) VALUES 
(150.00, 'COMPLETED'),
(200.50, 'PENDING'),
(75.25, 'PAID'),
(120.00, 'CANCELLED');

-- 📌 Bảng chi tiết đơn hàng
CREATE TABLE IF NOT EXISTS OrderDetails (
    OrderDetailID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    ItemID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
    FOREIGN KEY (ItemID) REFERENCES MenuItems(ItemID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng OrderDetails
INSERT IGNORE INTO OrderDetails (OrderID, ItemID, Quantity, Price) VALUES 
(1, 1, 2, 75.00),
(1, 2, 1, 150.00),
(2, 1, 1, 75.00),  -- Sửa lại giá cho ItemID 1
(3, 3, 3, 25.00);

-- 📌 Bảng báo cáo doanh thu
CREATE TABLE IF NOT EXISTS RevenueReports (
    ReportID INT PRIMARY KEY AUTO_INCREMENT,
    ReportMonth VARCHAR(7) NOT NULL,  -- Tháng báo cáo, ví dụ '2025-02'
    TotalRevenue DECIMAL(10, 2) NOT NULL,  -- Doanh thu tổng
    TotalQuantity INT NOT NULL  -- Số lượng món bán ra
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng RevenueReports
INSERT IGNORE INTO RevenueReports (ReportMonth, TotalRevenue, TotalQuantity) VALUES 
('2025-01', 5000.00, 200),
('2025-02', 6000.50, 250),
('2025-03', 7000.75, 300);