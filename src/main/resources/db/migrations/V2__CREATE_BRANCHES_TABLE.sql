CREATE TABLE IF NOT EXISTS branches (
    branch_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    franchise_id BIGINT NOT NULL,
    CONSTRAINT fk_franchise FOREIGN KEY (franchise_id) REFERENCES franchises(franchise_id)
);
