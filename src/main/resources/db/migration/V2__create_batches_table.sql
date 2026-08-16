-- Migration: Create batches table
-- Version: 2
-- Description: Initial creation for batches table

CREATE TABLE IF NOT EXISTS batches (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    external_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    product_id  BIGINT NOT NULL,
    quantity NUMERIC(19,2) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    expiry_date DATE NOT NULL,
    shelf_location VARCHAR(100) NOT NULL,

    CONSTRAINT FK_product_batch
        FOREIGN KEY (product_id) 
        REFERENCES products(id),
    CONSTRAINT chk_positive_quantity
        CHECK (quantity > 0)
);

-- Speed up JOIN queries between products and batches
CREATE INDEX IF NOT EXISTS idx_batches_product_id ON batches(product_id);