CREATE DATABASE employee_management;

USE employee_management;

CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
);

INSERT INTO employees (name, position, email, phone) VALUES 
('Nguyễn Văn A', 'Nhân viên', 'vana@example.com', '0123456789'),
('Trần Thị B', 'Quản lý', 'btran@example.com', '0987654321');

select * from employees;

CREATE TABLE leave_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    leave_start DATE,
    leave_end DATE,
    reason VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

INSERT INTO leave_history (employee_id, leave_start, leave_end, reason) VALUES 
(1, '2025-02-01', '2025-02-05', 'Nghỉ phép cá nhân'),
(2, '2025-02-10', '2025-02-15', 'Nghỉ ốm');
