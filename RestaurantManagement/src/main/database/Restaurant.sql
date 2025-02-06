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
                            Name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- 📌 Bảng thực đơn
CREATE TABLE MenuItems (
                           ItemID INT PRIMARY KEY AUTO_INCREMENT,
                           Name VARCHAR(100) NOT NULL,
                           Price DECIMAL(10, 2) NOT NULL,
                           Description TEXT,
                           CategoryID INT NOT NULL,
                           CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 📌 Chèn dữ liệu vào bảng Categories
INSERT INTO Categories (Name) VALUES
                                  ('Món chay'),
                                  ('Món nướng'),
                                  ('Lẩu'),
                                  ('Thức uống pha chế'),
                                  ('Thức uống đóng lon');

-- 📌 Chèn dữ liệu vào bảng MenuItems
INSERT INTO MenuItems (Name, Price, Description, CategoryID) VALUES
                                                                 ('Đậu hũ chiên sả', 45000, 'Đậu hũ chiên giòn, thơm mùi sả.', 1),
                                                                 ('Rau xào tỏi', 35000, 'Rau xanh xào với tỏi thơm lừng.', 1),
                                                                 ('Bò nướng lá lốt', 89000, 'Bò cuốn lá lốt nướng thơm ngon.', 2),
                                                                 ('Gà nướng mật ong', 99000, 'Gà nướng tẩm mật ong, thơm ngon.', 2),
                                                                 ('Lẩu thái hải sản', 199000, 'Lẩu cay chua, hải sản tươi.', 3),
                                                                 ('Lẩu bò nhúng giấm', 179000, 'Bò nhúng giấm chua ngọt.', 3),
                                                                 ('Trà đào cam sả', 35000, 'Trà đào pha với cam và sả.', 4),
                                                                 ('Cà phê sữa đá', 29000, 'Cà phê pha với sữa đặc và đá.', 4),
                                                                 ('Coca-Cola', 20000, 'Nước ngọt Coca-Cola lon 330ml.', 5),
                                                                 ('Pepsi', 20000, 'Nước ngọt Pepsi lon 330ml.', 5);


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
