SET SEARCH_PATH = public;

CREATE SEQUENCE client_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE account_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE payment_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE account_balance_change_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE account (
    account_id BIGINT PRIMARY KEY,
    version INTEGER NOT NULL,
    client_id BIGINT NOT NULL,
    name VARCHAR(255),
    balance_amount NUMERIC(38, 2) CHECK (balance_amount >= 0),
    balance_currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE account_balance_change (
    account_balance_change_id BIGINT PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    account_version INTEGER NOT NULL,
    amount NUMERIC(38, 2) NOT NULL
);

CREATE TABLE client (
    client_id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE payment (
    payment_id BIGINT PRIMARY KEY,
    client_id BIGINT,
    exchange_rate NUMERIC(19, 9),
    created_at TIMESTAMP
);

ALTER TABLE account_balance_change
ADD CONSTRAINT fk_payment_id
FOREIGN KEY (payment_id) REFERENCES payment(payment_id);

ALTER TABLE account_balance_change
ADD CONSTRAINT fk_account_id
FOREIGN KEY (account_id) REFERENCES account(account_id);

CREATE INDEX IF NOT EXISTS idx_account_client_id ON account(client_id);