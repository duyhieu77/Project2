CREATE DATABASE Restaurant2;
USE Restaurant2;

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
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

INSERT INTO Roles (RoleName) VALUES ('Quản lý'), ('Nhân viên'), ('Đầu bếp'), ('Thu ngân');

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
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

INSERT INTO Categories (Name) VALUES ('Món chay'), ('Món nướng'), ('Lẩu'), ('Thức uống pha chế'), ('Thức uống đóng lon');

CREATE TABLE Ingredients (
    IngredientID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Quantity INT DEFAULT 0,
    Unit VARCHAR(20),
    MinimumThreshold INT NOT NULL
);

CREATE TABLE TransactionTypes (
    TransactionTypeID INT PRIMARY KEY AUTO_INCREMENT,
    TypeName VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IngredientTransactions (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    IngredientID INT NOT NULL,
    TransactionTypeID INT NOT NULL,
    Quantity INT NOT NULL,
    TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Notes TEXT,
    FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID),
    FOREIGN KEY (TransactionTypeID) REFERENCES TransactionTypes(TransactionTypeID)
);

INSERT INTO TransactionTypes (TypeName) VALUES ('Nhập kho'), ('Xuất kho'), ('Hủy nguyên liệu');

CREATE TABLE CustomerTypes (
    TypeID INT PRIMARY KEY AUTO_INCREMENT,
    TypeName VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Orders (
    OrderID INT PRIMARY KEY AUTO_INCREMENT,
    TypeID INT NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (TypeID) REFERENCES CustomerTypes(TypeID)
);

CREATE TABLE OrderDetails (
    OrderDetailID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    ItemID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ItemID) REFERENCES MenuItems(ItemID)
);

CREATE TABLE OrderAdjustments (
    AdjustmentID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    Type ENUM('Hủy món', 'Đổi món') NOT NULL,
    AdjustmentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);

INSERT INTO CustomerTypes (TypeName) VALUES ('Mang đi'), ('Tại chỗ'), ('Giao hàng');

CREATE TABLE Tables (
    TableID INT PRIMARY KEY AUTO_INCREMENT,
    TableNumber INT UNIQUE NOT NULL,
    Status ENUM('Trống', 'Đang sử dụng', 'Đã đặt trước') DEFAULT 'Trống',
    Capacity INT DEFAULT 4
);

CREATE TABLE Reservations (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    TableID INT NOT NULL,
    CustomerName VARCHAR(100),
    GuestsCount INT NOT NULL,
    ReservationDate DATE NOT NULL,
    ReservationTime TIME NOT NULL,
    FOREIGN KEY (TableID) REFERENCES Tables(TableID)
);

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(100) NOT NULL,
    DateOfBirth DATE,
    PhoneNumber VARCHAR(15),
    Address TEXT,
    RoleID INT NOT NULL,
    HireDate DATE NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

CREATE TABLE LeaveRecords (
    LeaveID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Reason TEXT,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);

CREATE VIEW RevenueReport AS
SELECT 
    DATE(OrderDate) AS ReportDate,
    SUM(TotalAmount) AS TotalRevenue
FROM Orders
GROUP BY DATE(OrderDate);

CREATE VIEW PopularItems AS
SELECT 
    mi.Name AS ItemName,
    COUNT(od.ItemID) AS TimesOrdered
FROM OrderDetails od
JOIN MenuItems mi ON od.ItemID = mi.ItemID
GROUP BY od.ItemID
ORDER BY TimesOrdered DESC;

CREATE TABLE StockAlerts (
    AlertID INT PRIMARY KEY AUTO_INCREMENT,
    IngredientID INT NOT NULL,
    AlertMessage VARCHAR(255),
    AlertDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Processed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID)
);
