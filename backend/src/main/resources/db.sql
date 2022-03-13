CREATE DATABASE shoppingweb;

CREATE TABLE account
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    email      VARCHAR(150),
    role_id    INTEGER REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE role
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(30) UNIQUE NOT NULL
);

INSERT INTO role VALUES(1, 'CUSTOMER');
INSERT INTO role VALUES(2, 'ADMIN');

CREATE TABLE item
(
    id          INTEGER PRIMARY KEY,
    name        VARCHAR(200)     NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    brand       VARCHAR(70),
    description VARCHAR(5000),
    rate        DOUBLE PRECISION,
    for_sale    BOOLEAN          NOT NULL,
    thumbnail   VARCHAR(5000),
    quantity    INTEGER
);

CREATE TABLE shopping_cart
(
    id           SERIAL PRIMARY KEY,
    account_id  INTEGER REFERENCES account (id) ON DELETE CASCADE,
    total_amount DOUBLE PRECISION NOT NULL,
    UNIQUE (account_id)
);

CREATE TABLE payment
(
    id          SERIAL PRIMARY KEY,
    customer_id INTEGER REFERENCES customer (id) ON DELETE CASCADE,
    type        VARCHAR(100),
    number      VARCHAR(500),
    enabled     BOOLEAN DEFAULT FALSE
);

CREATE TABLE address
(
    id          SERIAL PRIMARY KEY,
    customer_id INTEGER REFERENCES customer (id) ON DELETE CASCADE,
    content     VARCHAR(500),
    enabled     BOOLEAN DEFAULT FALSE
);

CREATE TABLE order_history
(
    id           SERIAL PRIMARY KEY,
    customer_id  INTEGER REFERENCES customer (id) ON DELETE CASCADE,
    total_amount DOUBLE PRECISION NOT NULL,
    payment_id   INTEGER REFERENCES payment (id) ON DELETE CASCADE,
    address_id   INTEGER REFERENCES address (id) ON DELETE CASCADE
);

CREATE TABLE shopping_item
(
    id               SERIAL PRIMARY KEY,
    item_id          INTEGER REFERENCES item (id) ON DELETE CASCADE,
    quantity         INTEGER DEFAULT 0,
    price            DOUBLE PRECISION NOT NULL,
    shopping_cart_id INTEGER REFERENCES shopping_cart (id) ON DELETE CASCADE,
    order_id         INTEGER REFERENCES order_history (id) ON DELETE CASCADE
);
