INSERT INTO tb_category (name, created_at) VALUES ('books', NOW());
INSERT INTO tb_category (name,created_at) VALUES  ('comics', NOW());
INSERT INTO tb_category (name, created_at) VALUES  ('hardware', NOW());
INSERT INTO tb_category (name, created_at) VALUES  ('software', NOW());
INSERT INTO tb_category (name, created_at) VALUES  ('cellphones', NOW());


INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('A Storm of Swords', 'A song of ice and fire', 65.70, 'https://rrmartin.com/storm', TIMESTAMP WITH TIME ZONE '2026-04-01T10:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Clean Code', 'A handbook of agile software craftsmanship', 89.90, 'https://books.example.com/clean-code', TIMESTAMP WITH TIME ZONE '2026-04-01T09:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('The Pragmatic Programmer', 'Your journey to mastery', 79.50, 'https://books.example.com/pragmatic', TIMESTAMP WITH TIME ZONE '2026-04-01T09:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Batman: Year One', 'The dark knight origin story', 45.00, 'https://comics.example.com/batman-year-one' TIMESTAMP WITH TIME ZONE '2026-04-01T09:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Watchmen', 'Who watches the watchmen?', 59.90, 'https://comics.example.com/watchmen', TIMESTAMP WITH TIME ZONE '2025-04-01T09:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Mechanical Keyboard K70', 'RGB backlit mechanical gaming keyboard', 349.90, 'https://hardware.example.com/k70', TIMESTAMP WITH TIME ZONE '2026-04-01T08:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('SSD 1TB NVMe', 'High-speed solid state drive for gaming and work', 479.00, 'https://hardware.example.com/ssd-1tb', TIMESTAMP WITH TIME ZONE '2023-04-01T09:07:55Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('IntelliJ IDEA Ultimate', 'The leading Java and Kotlin IDE', 199.90, 'https://software.example.com/intellij', TIMESTAMP WITH TIME ZONE '2026-04-01T09:07:25Z');
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Adobe Photoshop', 'Professional photo editing software', 129.90, 'https://software.example.com/photoshop', TIMESTAMP WITH TIME ZONE '2026-04-01T09:21:55Z');

INSERT INTO tb_category_product (product_id, category_id) VALUES (1, 1);  -- A Storm of Swords → Books
INSERT INTO tb_category_product (product_id, category_id) VALUES (2, 1);  -- Clean Code → Books
INSERT INTO tb_category_product (product_id, category_id) VALUES (3, 1);  -- The Pragmatic Programmer → Books
INSERT INTO tb_category_product (product_id, category_id) VALUES (4, 2);  -- Batman: Year One → Comics
INSERT INTO tb_category_product (product_id, category_id) VALUES (5, 2);  -- Watchmen → Comics
INSERT INTO tb_category_product (product_id, category_id) VALUES (6, 3);  -- Mechanical Keyboard → Hardware
INSERT INTO tb_category_product (product_id, category_id) VALUES (7, 3);  -- SSD 1TB → Hardware
INSERT INTO tb_category_product (product_id, category_id) VALUES (8, 4);  -- IntelliJ IDEA → Software


CREATE INDEX idx_cat_id ON tb_category (id);
CREATE INDEX idx_cat_name ON tb_category (name);
CREATE INDEX idx_prod_id ON tb_product (id);