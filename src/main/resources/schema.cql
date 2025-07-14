CREATE KEYSPACE IF NOT EXISTS marketplace_service_db
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE marketplace_service_db;

CREATE TABLE IF NOT EXISTS products (
                                        id UUID PRIMARY KEY,
                                        name TEXT,
                                        description TEXT,
                                        seller_id UUID,
                                        price DECIMAL,
                                        quantity INT,
                                        status TEXT,
                                        created_at TIMESTAMP,
                                        updated_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS products_by_seller ON products(seller_id);
CREATE INDEX IF NOT EXISTS products_by_status ON products(status);

CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY,
                                     email TEXT,
                                     password TEXT,
                                     first_name TEXT,
                                     last_name TEXT,
                                     role TEXT,
                                     phone TEXT,
                                     created_at TIMESTAMP,
                                     is_active BOOLEAN
);

CREATE INDEX IF NOT EXISTS users_by_email ON users(email);

CREATE TABLE IF NOT EXISTS carts (
                                     id UUID,
                                     user_id UUID,
                                     total_amount DECIMAL,
                                     created_at TIMESTAMP,
                                     updated_at TIMESTAMP,
                                     PRIMARY KEY (user_id, id)
    );

CREATE TABLE IF NOT EXISTS cart_items (
                                          id UUID,
                                          cart_id UUID,
                                          product_id UUID,
                                          quantity INT,
                                          unit_price DECIMAL,
                                          added_at TIMESTAMP,
                                          PRIMARY KEY ((cart_id), id)
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id UUID PRIMARY KEY,
                                      user_id UUID,
                                      order_number TEXT,
                                      total_amount DECIMAL,
                                      status TEXT,
                                      payment_status TEXT,
                                      payment_attempts INT,
                                      payment_reference TEXT,
                                      created_at TIMESTAMP,
                                      updated_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS orders_by_user ON orders(user_id);

CREATE TABLE IF NOT EXISTS order_items (
                                           id UUID,
                                           order_id UUID,
                                           product_id UUID,
                                           quantity INT,
                                           unit_price DECIMAL,
                                           subtotal DECIMAL,
                                           organisation_id UUID,
                                           PRIMARY KEY ((order_id), id)
    );

CREATE TABLE IF NOT EXISTS addresses (
                                         id UUID PRIMARY KEY,
                                         user_id UUID,
                                         street TEXT,
                                         city TEXT,
                                         state TEXT,
                                         country TEXT,
                                         zip_code TEXT,
                                         is_default BOOLEAN,
                                         created_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS addresses_by_user ON addresses(user_id);

CREATE TABLE IF NOT EXISTS wishlists (
                                         id UUID PRIMARY KEY,
                                         user_id UUID,
                                         created_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS wishlists_by_user ON wishlists(user_id);

CREATE TABLE IF NOT EXISTS wishlist_items (
                                              id UUID,
                                              wishlist_id UUID,
                                              product_id UUID,
                                              added_at TIMESTAMP,
                                              PRIMARY KEY ((wishlist_id), id)
    );

CREATE INDEX IF NOT EXISTS wishlist_items_by_product ON wishlist_items(product_id);

CREATE TABLE IF NOT EXISTS reviews (
                                       user_id UUID,
                                       product_id UUID,
                                       id UUID,
                                       rating INT,
                                       comment TEXT,
                                       is_verified BOOLEAN,
                                       created_at TIMESTAMP,
                                       updated_at TIMESTAMP,
                                       PRIMARY KEY ((user_id, product_id), id)
    );

CREATE TYPE IF NOT EXISTS invoice_items (
    description TEXT,
    quantity INT,
    unit_price DECIMAL,
    total DECIMAL
);

CREATE TABLE IF NOT EXISTS invoices (
                                        invoice_id TEXT PRIMARY KEY,
                                        customer_id TEXT,
                                        amount DECIMAL,
                                        issue_date DATE,
                                        due_date DATE,
                                        status TEXT
);

CREATE TABLE IF NOT EXISTS organisation (
                                            id UUID PRIMARY KEY,
                                            name TEXT,
                                            description TEXT,
                                            transferable BOOLEAN
);