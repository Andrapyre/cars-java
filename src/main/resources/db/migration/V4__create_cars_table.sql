CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS cars (
                       id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
                       customer_id INT NOT NULL,
                       model VARCHAR(50) NOT NULL,
                       make VARCHAR(50) NOT NULL,
                       produced_in INT NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_customer
                           FOREIGN KEY(customer_id)
                               REFERENCES customers(id)
                               ON DELETE CASCADE
);