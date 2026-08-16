-- Migration: Create products table
-- Version: 1
-- Description: Initial creation for products table

CREATE TABLE IF NOT EXISTS products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    external_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    sku VARCHAR(100) NOT NULL UNIQUE,
    brand VARCHAR(100) NOT NULL,
    price DECIMAL(18,8),
    category VARCHAR(100),
    quantity NUMERIC(19,2) NOT NULL DEFAULT 2.00,
    unit VARCHAR(50) NOT NULL
);