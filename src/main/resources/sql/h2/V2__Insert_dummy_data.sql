-- Insert Dummy Products
INSERT INTO products (name, description, category, creation_date) VALUES
('Product A', 'Description for product A', 'Category 1', '2023-01-01'),
('Product B', 'Description for product B', 'Category 2', '2023-01-02');

-- Insert Dummy Clients
INSERT INTO clients (name, last_name, mobile) VALUES
('John', 'Doe', '1234567890'),
('Jane', 'Doe', '0987654321');

-- Insert Dummy Sales with Seller Name
INSERT INTO sales (creation_date, client_id, seller_name, total) VALUES
('2023-01-10', 1, 'Seller A', 100.00),
('2023-01-11', 2, 'Seller B', 150.00);

-- Insert Dummy Transactions
INSERT INTO transactions (sale_id, product_id, quantity, price) VALUES
(1, 1, 2, 50.00),
(2, 2, 3, 50.00);

-- Insert Dummy Sale Transaction Logs
INSERT INTO sale_transaction_logs (transaction_id, old_quantity, new_quantity, old_price, new_price) VALUES
(1, 2, 3, 50.00, 45.00), 
(2, 3, 2, 50.00, 55.00);