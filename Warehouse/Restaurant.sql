CREATE DATABASE Restaurant;
USE Restaurant;

-- Bảng vai trò
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY AUTO_INCREMENT,
    RoleName VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    RoleID INT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID) ON DELETE CASCADE
);

INSERT INTO Roles (RoleName) VALUES ('Quản lý'), ('Nhân viên'), ('Đầu bếp'), ('Thu ngân');

-- Bảng danh mục món ăn
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE MenuItems (
    ItemID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Description TEXT,
    CategoryID INT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID) ON DELETE CASCADE
);

INSERT INTO Categories (Name) VALUES ('Món chay'), ('Món nướng'), ('Lẩu'), ('Thức uống pha chế'), ('Thức uống đóng lon');

-- Bảng danh mục nguyên liệu
CREATE TABLE IngredientCategories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL UNIQUE
);

-- Bảng nguyên liệu
CREATE TABLE Ingredients (
    IngredientID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    CategoryID INT,
    Unit VARCHAR(50) NOT NULL, 
    Stock FLOAT NOT NULL DEFAULT 0, 
    MinStock FLOAT NOT NULL DEFAULT 0, 
    PricePerUnit DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (CategoryID) REFERENCES IngredientCategories(CategoryID) ON DELETE SET NULL
);



-- Bảng loại giao dịch kho
CREATE TABLE TransactionTypes (
    TransactionTypeID INT PRIMARY KEY AUTO_INCREMENT,
    TypeName ENUM('Nhập kho', 'Điều chỉnh tồn kho', 'Hủy nguyên liệu') NOT NULL UNIQUE
);

CREATE TABLE InventoryTransactions (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    TransactionTypeID INT NOT NULL,          
    SupplierName VARCHAR(255),               
    IngredientID INT NOT NULL,               
    Quantity FLOAT NOT NULL,                
    Unit VARCHAR(50) NOT NULL, 
    Price DECIMAL(10,2),                    
    Note TEXT,                              
    TransactionDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID) ON DELETE CASCADE,
    FOREIGN KEY (TransactionTypeID) REFERENCES TransactionTypes(TransactionTypeID) ON DELETE CASCADE
);

-- Bảng lưu trữ tồn kho hàng ngày
CREATE TABLE DailyStock (
    StockID INT PRIMARY KEY AUTO_INCREMENT,
    IngredientID INT NOT NULL,
    Name VARCHAR(255) NOT NULL,
    CategoryID INT,
    Unit VARCHAR(50) NOT NULL,
    Stock FLOAT NOT NULL,
    MinStock FLOAT NOT NULL,
    PricePerUnit DECIMAL(10,2) NOT NULL,
    Date DATE NOT NULL,
    FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID) ON DELETE CASCADE,
    UNIQUE (IngredientID, Date) 
);


-- Bảng loại khách hàng
CREATE TABLE CustomerTypes (
    TypeID INT PRIMARY KEY AUTO_INCREMENT,
    TypeName VARCHAR(50) NOT NULL UNIQUE
);

-- Bảng đơn hàng
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY AUTO_INCREMENT,
    TypeID INT NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (TypeID) REFERENCES CustomerTypes(TypeID) ON DELETE CASCADE
);

-- Bảng chi tiết đơn hàng
CREATE TABLE OrderDetails (
    OrderDetailID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    ItemID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
    FOREIGN KEY (ItemID) REFERENCES MenuItems(ItemID) ON DELETE CASCADE
);

-- Bảng điều chỉnh đơn hàng
CREATE TABLE OrderAdjustments (
    AdjustmentID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    Type ENUM('Hủy món', 'Đổi món') NOT NULL,
    AdjustmentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE
);

INSERT INTO CustomerTypes (TypeName) VALUES ('Mang đi'), ('Tại chỗ'), ('Giao hàng');

-- Bảng bàn ăn (Đổi tên từ "Tables" thành "RestaurantTables")
CREATE TABLE RestaurantTables (
    TableID INT PRIMARY KEY AUTO_INCREMENT,
    TableNumber INT UNIQUE NOT NULL,
    Status ENUM('Trống', 'Đang sử dụng', 'Đã đặt trước') DEFAULT 'Trống',
    Capacity INT DEFAULT 4
);

-- Bảng đặt bàn
CREATE TABLE Reservations (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    TableID INT NOT NULL,
    CustomerName VARCHAR(100),
    GuestsCount INT NOT NULL,
    ReservationDate DATE NOT NULL,
    ReservationTime TIME NOT NULL,
    FOREIGN KEY (TableID) REFERENCES RestaurantTables(TableID) ON DELETE CASCADE
);

-- Bảng nhân viên
CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(100) NOT NULL,
    DateOfBirth DATE,
    PhoneNumber VARCHAR(15),
    Address TEXT,
    RoleID INT NOT NULL,
    HireDate DATE NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID) ON DELETE CASCADE
);

-- Bảng nghỉ phép nhân viên
CREATE TABLE LeaveRecords (
    LeaveID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Reason TEXT,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);

-- View báo cáo doanh thu
CREATE VIEW RevenueReport AS
SELECT 
    DATE(OrderDate) AS ReportDate,
    SUM(TotalAmount) AS TotalRevenue
FROM Orders
GROUP BY DATE(OrderDate);


-- View món bán chạy
CREATE VIEW PopularItems AS
SELECT 
    mi.Name AS ItemName,
    COUNT(od.ItemID) AS TimesOrdered
FROM OrderDetails od
JOIN MenuItems mi ON od.ItemID = mi.ItemID
GROUP BY od.ItemID
ORDER BY TimesOrdered DESC;
