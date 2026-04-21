CREATE TABLE IF NOT EXISTS products (
    product_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    branch_id BIGINT NOT NULL,
    CONSTRAINT fk_branch FOREIGN KEY (branch_id) REFERENCES branches(branch_id)
);
