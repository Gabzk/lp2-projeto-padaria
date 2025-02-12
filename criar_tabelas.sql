-- Cria o banco de dados (caso ainda não exista) e seleciona-o
CREATE DATABASE IF NOT EXISTS padaria;
USE padaria;

-- Remove as tabelas existentes (sempre na ordem de dependência)
DROP TABLE IF EXISTS venda_produtos;
DROP TABLE IF EXISTS vendas;
DROP TABLE IF EXISTS produtos;

-- Criação da tabela de produtos
CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL DEFAULT 'Outros',
    preco DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    quantidade INT NOT NULL DEFAULT 0,
    validade DATE NOT NULL DEFAULT '2099-12-31'
);

-- Criação da tabela de vendas
CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00
);

-- Criação da tabela de venda_produtos
CREATE TABLE venda_produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venda_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (venda_id) REFERENCES vendas(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE CASCADE
);

-- Inserção de produtos
INSERT INTO produtos (nome, categoria, preco, quantidade, validade) VALUES
    ('Pão Francês', 'Pães', 0.50, 100, '2025-01-25'),
    ('Bolo de Chocolate', 'Doces', 15.00, 10, '2025-01-30'),
    ('Café em Pó', 'Bebidas', 12.50, 20, '2025-03-30'),
    ('Leite Integral', 'Bebidas', 5.00, 50, '2025-02-15'),
    ('Torta de Frango', 'Salgados', 25.00, 5, '2025-01-28'),
    ('Pão Integral', 'Pães', 1.00, 80, '2025-01-27'),
    ('Bolo de Cenoura', 'Doces', 18.00, 8, '2025-02-05'),
    ('Pão de Queijo', 'Salgados', 0.80, 150, '2025-01-26'),
    ('Empada de Frango', 'Salgados', 5.00, 60, '2025-01-29'),
    ('Croissant', 'Pães', 3.50, 40, '2025-01-24'),
    ('Muffin Blueberry', 'Doces', 4.00, 30, '2025-01-31'),
    ('Bolo de Morango', 'Doces', 20.00, 6, '2025-02-10'),
    ('Sanduíche Natural', 'Salgados', 10.00, 25, '2025-01-28'),
    ('Suco de Laranja', 'Bebidas', 7.00, 35, '2025-02-20'),
    ('Água Mineral', 'Bebidas', 2.00, 100, '2025-03-01'),
    ('Biscoito de Polvilho', 'Salgados', 3.00, 200, '2025-02-15'),
    ('Pão de Batata', 'Pães', 1.20, 90, '2025-01-29'),
    ('Quiche de Alho-Poró', 'Salgados', 12.00, 15, '2025-02-08'),
    ('Bolo de Fubá', 'Doces', 16.00, 12, '2025-02-03'),
    ('Pão de Milho', 'Pães', 0.70, 110, '2025-01-27'),
    ('Empada de Palmito', 'Salgados', 6.50, 50, '2025-02-12'),
    ('Cookie', 'Doces', 2.50, 120, '2025-03-05'),
    ('Bolo Nobre de Nozes', 'Doces', 150.00, 5, '2025-12-31'),
    ('Torta Premium de Frutos do Mar', 'Salgados', 250.00, 3, '2025-11-30'),
    ('Café Gourmet', 'Bebidas', 50.00, 30, '2025-12-31'),
    ('Pão Artesanal', 'Pães', 20.00, 40, '2025-10-15'),
    ('Cheesecake Especial', 'Doces', 80.00, 10, '2025-12-15'),
    ('Chocolate Belga', 'Doces', 100.00, 20, '2025-11-20');
