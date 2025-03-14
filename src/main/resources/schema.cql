-- CREATE TABLE IF NOT EXISTS marketplace_db.products (
--                                                        id UUID PRIMARY KEY,
--                                                        name text,
--                                                        description text,
--                                                        seller_id UUID,  // This is the correct column name
--                                                        price decimal,
--                                                        quantity int,
--                                                        status text,
--                                                        created_at timestamp,
--                                                        updated_at timestamp
-- );
--
-- CREATE INDEX IF NOT EXISTS products_by_seller ON marketplace_db.products(seller_id);
-- CREATE INDEX IF NOT EXISTS products_by_status ON marketplace_db.products(status);
--
-- //users
--
--
-- CREATE TABLE IF NOT EXISTS marketplace_db.users (
--                                                     id UUID PRIMARY KEY,
--                                                     email text,
--                                                     password text,
--                                                     first_name text,
--                                                     last_name text,
--                                                     role text,
--                                                     phone text,
--                                                     created_at timestamp,
--                                                     is_active boolean
-- );
--
-- CREATE INDEX IF NOT EXISTS users_by_email ON marketplace_db.users(email);
--
--
--
-- // carts
--
-- CREATE TABLE IF NOT EXISTS marketplace_db.carts (
--                                                     id UUID,
--                                                     user_id UUID,
--                                                     total_amount decimal,
--                                                     created_at timestamp,
--                                                     updated_at timestamp,
--                                                     PRIMARY KEY (user_id, id)
-- );
--
-- CREATE TABLE IF NOT EXISTS marketplace_db.cart_items (
--                                                          id UUID,
--                                                          cart_id UUID,
--                                                          product_id UUID,
--                                                          quantity int,
--                                                          unit_price decimal,
--                                                          added_at timestamp,
--                                                          PRIMARY KEY ((cart_id), id)
-- );
--
--
-- // order management
--
-- CREATE TABLE IF NOT EXISTS marketplace_db.orders (
--                                                      id UUID,
--                                                      user_id UUID,
--                                                      order_number text,
--                                                      total_amount decimal,
--                                                      status text,
--                                                      payment_status text,
--                                                      created_at timestamp,
--                                                      updated_at timestamp,
--                                                      PRIMARY KEY (user_id, created_at, id)
-- ) WITH CLUSTERING ORDER BY (created_at DESC);
--
-- CREATE TABLE IF NOT EXISTS marketplace_db.order_items (
--                                                           id UUID,
--                                                           order_id UUID,
--                                                           product_id UUID,
--                                                           quantity int,
--                                                           unit_price decimal,
--                                                           subtotal decimal,
--                                                           PRIMARY KEY ((order_id), id)
-- );









CREATE KEYSPACE IF NOT EXISTS marketplace_service_db
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE marketplace_service_db;

CREATE TABLE IF NOT EXISTS carts (
                                     id UUID,
                                     user_id UUID,
                                     total_amount decimal,
                                     created_at timestamp,
                                     updated_at timestamp,
                                     PRIMARY KEY (user_id, id)
);

CREATE TABLE IF NOT EXISTS cart_items (
                                          id UUID,
                                          cart_id UUID,
                                          product_id UUID,
                                          quantity int,
                                          unit_price decimal,
                                          added_at timestamp,
                                          PRIMARY KEY ((cart_id), id)
);

-- CREATE TABLE IF NOT EXISTS orders (
--                                       id UUID,
--                                       user_id UUID,
--                                       order_number text,
--                                       total_amount decimal,
--                                       status text,
--                                       payment_status text,
--                                       created_at timestamp,
--                                       updated_at timestamp,
--                                       PRIMARY KEY (user_id, created_at, id)
-- ) WITH CLUSTERING ORDER BY (created_at DESC);
DROP TABLE marketplace_service_db.orders;

CREATE TABLE IF NOT EXISTS orders (
                                      id UUID PRIMARY KEY,
                                      user_id UUID,
                                      order_number text,
                                      total_amount decimal,
                                      status text,
                                      payment_status text,
                                      payment_attempts int,
                                      payment_reference text,
                                      created_at timestamp,
                                      updated_at timestamp
);
--ALTER TABLE orders ADD payment_transaction_code UUID;

CREATE INDEX IF NOT EXISTS orders_by_user ON orders(user_id);

CREATE TABLE IF NOT EXISTS order_items (
                                           id UUID,
                                           order_id UUID,
                                           product_id UUID,
                                           quantity int,
                                           unit_price decimal,
                                           subtotal decimal,
                                           organisation_id UUID,
                                           PRIMARY KEY ((order_id), id)
);


CREATE TABLE IF NOT EXISTS addresses (
                                         id UUID PRIMARY KEY,
                                         user_id UUID,
                                         street text,
                                         city text,
                                         state text,
                                         country text,
                                         zip_code text,
                                         is_default boolean,
                                         created_at timestamp
);

CREATE INDEX IF NOT EXISTS addresses_by_user ON addresses(user_id);



-- Wishlist table
CREATE TABLE IF NOT EXISTS wishlists (
                                         id UUID PRIMARY KEY,
                                         user_id UUID,
                                         created_at timestamp
);

CREATE INDEX IF NOT EXISTS wishlists_by_user ON wishlists(user_id);

-- Wishlist items table
CREATE TABLE IF NOT EXISTS wishlist_items (
                                              id UUID,
                                              wishlist_id UUID,
                                              product_id UUID,
                                              added_at timestamp,
                                              PRIMARY KEY ((wishlist_id), id)
);

CREATE INDEX IF NOT EXISTS wishlist_items_by_product ON wishlist_items(product_id);


-- Reviews table
CREATE TABLE IF NOT EXISTS reviews (
                                       user_id UUID,
                                       product_id UUID,
                                       id UUID,
                                       rating int,
                                       comment text,
                                       is_verified boolean,
                                       created_at timestamp,
                                       updated_at timestamp,
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
                                        status TEXT,
                                        -- items LIST<FROZEN<item>>            -- Liste des items de la facture
);

CREATE TABLE IF NOT EXISTS Organisation (
                              id UUID PRIMARY KEY,
                              name TEXT,
                              description TEXT,
                              transferable BOOLEAN
);
