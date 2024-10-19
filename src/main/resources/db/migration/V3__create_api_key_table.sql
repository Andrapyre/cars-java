CREATE TABLE IF NOT EXISTS api_keys (
                       id SERIAL PRIMARY KEY,
                       customer_id INT NOT NULL,
                       api_key VARCHAR(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_customer
                           FOREIGN KEY(customer_id)
                               REFERENCES customers(id)
                               ON DELETE CASCADE
);