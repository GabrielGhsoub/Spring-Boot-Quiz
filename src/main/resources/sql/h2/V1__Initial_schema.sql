-- Create Products Table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255),
    creation_date DATE NOT NULL
);

-- Create Clients Table
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    mobile VARCHAR(20) NOT NULL
);

-- Create Sales Table with Seller Name
CREATE TABLE IF NOT EXISTS sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creation_date DATE NOT NULL,
    client_id INT,
    seller_name VARCHAR(255) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Create Transactions Table
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sale_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Create Log Table for Sale Transaction Updates
CREATE TABLE IF NOT EXISTS sale_transaction_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    old_quantity INT,
    new_quantity INT,
    old_price DECIMAL(10, 2),
    new_price DECIMAL(10, 2),
    FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);
