-- 📌 Tạo database
CREATE DATABASE IF NOT EXISTS Restaurant;
USE Restaurant;

-- 📌 Bảng người dùng
CREATE TABLE Users (
                       UserID INT PRIMARY KEY AUTO_INCREMENT,
                       Username VARCHAR(50) UNIQUE NOT NULL,
                       PasswordHash VARCHAR(255) NOT NULL,
                       Role ENUM('MANAGER','CHEF','CASHIER') NOT NULL,
                       CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 📌 Bảng danh mục món ăn
CREATE TABLE Categories (
                            CategoryID INT PRIMARY KEY AUTO_INCREMENT,
                            CategoryName VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 📌 Bảng thực đơn
CREATE TABLE MenuItems (
                           ItemID INT PRIMARY KEY AUTO_INCREMENT,
                           Name VARCHAR(100) NOT NULL,
                           Price DECIMAL(10,2) NOT NULL,
                           Description TEXT,
                           CategoryID INT NOT NULL,
                           CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 📌 Bảng nguyên liệu
CREATE TABLE Ingredients (
                             IngredientID INT PRIMARY KEY AUTO_INCREMENT,
                             Name VARCHAR(100) NOT NULL,
                             Quantity INT DEFAULT 0,
                             Unit VARCHAR(20),
                             MinimumThreshold INT NOT NULL
) ENGINE=InnoDB;

-- 📌 Bảng đơn hàng
CREATE TABLE Orders (
                        OrderID INT PRIMARY KEY AUTO_INCREMENT,
                        CustomerType ENUM('Mang đi', 'Tại chỗ') NOT NULL,
                        TotalAmount DECIMAL(10,2) NOT NULL,
                        OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 📌 Bảng chi tiết đơn hàng
CREATE TABLE OrderDetails (
                              OrderDetailID INT PRIMARY KEY AUTO_INCREMENT,
                              OrderID INT NOT NULL,
                              ItemID INT NOT NULL,
                              Quantity INT NOT NULL,
                              Price DECIMAL(10,2) NOT NULL,
                              FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
                              FOREIGN KEY (ItemID) REFERENCES MenuItems(ItemID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 📌 Bảng bàn ăn
CREATE TABLE Tables (
                        TableID INT PRIMARY KEY AUTO_INCREMENT,
                        TableNumber INT UNIQUE NOT NULL,
                        Status ENUM('Trống', 'Đang sử dụng', 'Đã đặt trước') DEFAULT 'Trống',
                        Capacity INT DEFAULT 4
) ENGINE=InnoDB;

-- 📌 Bảng đặt bàn
CREATE TABLE Reservations (
                              ReservationID INT PRIMARY KEY AUTO_INCREMENT,
                              TableID INT NOT NULL,
                              CustomerName VARCHAR(100),
                              GuestsCount INT NOT NULL,
                              ReservationDate DATE NOT NULL,
                              ReservationTime TIME NOT NULL,
                              FOREIGN KEY (TableID) REFERENCES Tables(TableID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 📌 Bảng nhân viên (Cập nhật)
CREATE TABLE Employees (
                           EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
                           FullName VARCHAR(100) NOT NULL,
                           DateOfBirth DATE,
                           PhoneNumber VARCHAR(15),
                           Email VARCHAR(100) UNIQUE,
                           Position ENUM('Quản lý', 'Nhân viên', 'Đầu bếp', 'Thu ngân') NOT NULL,
                           HireDate DATE NOT NULL,
                           Status ENUM('Đang làm việc', 'Đã nghỉ việc', 'Nghỉ phép') DEFAULT 'Đang làm việc'
) ENGINE=InnoDB;


-- 📌 Bảng cảnh báo nguyên liệu
CREATE TABLE StockAlerts (
                             AlertID INT PRIMARY KEY AUTO_INCREMENT,
                             IngredientID INT NOT NULL,
                             AlertMessage VARCHAR(255),
                             AlertDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 📌 Trigger cảnh báo nguyên liệu thấp
DELIMITER //
CREATE TRIGGER StockAlert AFTER UPDATE ON Ingredients
    FOR EACH ROW
BEGIN
    IF NEW.Quantity < NEW.MinimumThreshold THEN
        INSERT INTO StockAlerts (IngredientID, AlertMessage)
        VALUES (NEW.IngredientID, CONCAT('Nguyên liệu "', NEW.Name, '" chỉ còn ', NEW.Quantity, ' ', NEW.Unit, ' dưới mức tối thiểu!'));
    END IF;
END;
//
DELIMITER ;

SELECT * FROM Users WHERE Username = 'sonvau05';
