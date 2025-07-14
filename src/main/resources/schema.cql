
CREATE KEYSPACE IF NOT EXISTS marketplace_service_db
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE marketplace_service_db;

CREATE TYPE IF NOT EXISTS marketplace_service_db.invoice_items (
    description text,
    quantity int,
    unit_price decimal,
    total decimal
);

CREATE TABLE IF NOT EXISTS marketplace_service_db.addresses (
                                                  id uuid,
                                                  city text,
                                                  country text,
                                                  created_at timestamp,
                                                  is_default boolean,
                                                  state text,
                                                  street text,
                                                  user_id uuid,
                                                  zip_code text,
                                                  PRIMARY KEY (id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE INDEX IF NOT EXISTS addresses_by_user ON marketplace_service_db.addresses(user_id);


CREATE TABLE IF NOT EXISTS marketplace_service_db.cart_items (
                                                   cart_id uuid,
                                                   id uuid,
                                                   added_at timestamp,
                                                   product_id uuid,
                                                   quantity int,
                                                   unit_price decimal,
                                                   PRIMARY KEY (cart_id, id)
) WITH CLUSTERING ORDER BY (id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
    AND comment = ''
    AND compaction = {'class': 'SizeTieredCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND speculative_retry = '99.0PERCENTILE'
    AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE TABLE IF NOT EXISTS marketplace_service_db.carts (
                                              user_id uuid,
                                              id uuid,
                                              created_at timestamp,
                                              orderid uuid,
                                              total_amount decimal,
                                              updated_at timestamp,
                                              PRIMARY KEY (user_id, id)
) WITH CLUSTERING ORDER BY (id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
    AND comment = ''
    AND compaction = {'class': 'SizeTieredCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND speculative_retry = '99.0PERCENTILE'
    AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE TABLE IF NOT EXISTS marketplace_service_db.invoices (
                                                 invoice_id text,
                                                 amount decimal,
                                                 customer_id text,
                                                 due_date date,
                                                 issue_date date,
                                                 status text,
                                                 PRIMARY KEY (invoice_id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE TABLE IF NOT EXISTS marketplace_service_db.order_items (
                                                    order_id uuid,
                                                    id uuid,
                                                    organisation_id uuid,
                                                    product_id uuid,
                                                    quantity int,
                                                    subtotal decimal,
                                                    unit_price decimal,
                                                    PRIMARY KEY (order_id, id)
) WITH CLUSTERING ORDER BY (id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
    AND comment = ''
    AND compaction = {'class': 'SizeTieredCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND speculative_retry = '99.0PERCENTILE'
    AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE TABLE IF NOT EXISTS marketplace_service_db.orders (
                                               id uuid,
                                               created_at timestamp,
                                               order_number text,
                                               payment_attempts int,
                                               payment_reference text,
                                               payment_status text,
                                               status text,
                                               total_amount decimal,
                                               updated_at timestamp,
                                               user_id uuid,
                                               PRIMARY KEY (id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE INDEX IF NOT EXISTS orders_by_user ON marketplace_service_db.orders(user_id);


CREATE TABLE IF NOT EXISTS marketplace_service_db.organisation (
                                                     id uuid,
                                                     description text,
                                                     name text,
                                                     transferable boolean,
                                                     PRIMARY KEY (id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE TABLE IF NOT EXISTS marketplace_service_db.products (
                                                 id uuid,
                                                 created_at timestamp,
                                                 description text,
                                                 name text,
                                                 price decimal,
                                                 quantity int,
                                                 seller_id uuid,
                                                 status text,
                                                 updated_at timestamp,
                                                 PRIMARY KEY (id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE INDEX IF NOT EXISTS products_by_seller ON marketplace_service_db.products(seller_id);


CREATE INDEX IF NOT EXISTS products_by_status ON marketplace_service_db.products(status);


CREATE TABLE IF NOT EXISTS marketplace_service_db.reviews (
                                                user_id uuid,
                                                product_id uuid,
                                                id uuid,
                                                comment text,
                                                created_at timestamp,
                                                is_verified boolean,
                                                rating int,
                                                updated_at timestamp,
                                                PRIMARY KEY ((user_id, product_id), id)
    ) WITH CLUSTERING ORDER BY (id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
    AND comment = ''
    AND compaction = {'class': 'SizeTieredCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND speculative_retry = '99.0PERCENTILE'
    AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE TABLE IF NOT EXISTS marketplace_service_db.users (
                                              id uuid,
                                              created_at timestamp,
                                              email text,
                                              first_name text,
                                              is_active boolean,
                                              last_name text,
                                              password text,
                                              phone text,
                                              role text,
                                              PRIMARY KEY (id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE INDEX IF NOT EXISTS users_by_email ON marketplace_service_db.users(email);


CREATE TABLE IF NOT EXISTS marketplace_service_db.wishlist_items (
                                                       wishlist_id uuid,
                                                       id uuid,
                                                       added_at timestamp,
                                                       product_id uuid,
                                                       PRIMARY KEY (wishlist_id, id)
) WITH CLUSTERING ORDER BY (id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
    AND comment = ''
    AND compaction = {'class': 'SizeTieredCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND speculative_retry = '99.0PERCENTILE'
    AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE INDEX IF NOT EXISTS wishlist_items_by_product ON marketplace_service_db.wishlist_items(product_id);


CREATE TABLE IF NOT EXISTS marketplace_service_db.wishlists (
                                                  id uuid,
                                                  created_at timestamp,
                                                  user_id uuid,
                                                  PRIMARY KEY (id)
) WITH bloom_filter_fp_chance = 0.01
      AND caching = {'keys': 'ALL', 'rows_per_partition': 'ALL'}
      AND comment = ''
      AND compaction = {'class': 'SizeTieredCompactionStrategy'}
      AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
      AND crc_check_chance = 1
      AND default_time_to_live = 0
      AND gc_grace_seconds = 864000
      AND max_index_interval = 2048
      AND memtable_flush_period_in_ms = 0
      AND min_index_interval = 128
      AND speculative_retry = '99.0PERCENTILE'
      AND tombstone_gc = {'mode': 'timeout', 'propagation_delay_in_seconds': '3600'};


CREATE INDEX IF NOT EXISTS wishlists_by_user ON marketplace_service_db.wishlists(user_id);