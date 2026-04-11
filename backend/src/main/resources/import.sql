INSERT INTO tb_category (name, created_at) VALUES ('books', NOW());
INSERT INTO tb_category (name,created_at) VALUES  ('comics', NOW());
INSERT INTO tb_category (name, created_at) VALUES  ('hardware', NOW());
INSERT INTO tb_category (name, created_at) VALUES  ('software', NOW());
INSERT INTO tb_category (name, created_at) VALUES  ('cellphones', NOW());


INSERT INTO tb_product (name, description, price, img_url) VALUES ('A Storm of Swords', 'A song of ice and fire', 65.70, 'https://rrmartin.com/storm');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('Clean Code', 'A handbook of agile software craftsmanship', 89.90, 'https://books.example.com/clean-code');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('The Pragmatic Programmer', 'Your journey to mastery', 79.50, 'https://books.example.com/pragmatic');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('Batman: Year One', 'The dark knight origin story', 45.00, 'https://comics.example.com/batman-year-one');

INSERT INTO tb_product (name, description, price, img_url) VALUES ('Watchmen', 'Who watches the watchmen?', 59.90, 'https://comics.example.com/watchmen');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('Mechanical Keyboard K70', 'RGB backlit mechanical gaming keyboard', 349.90, 'https://hardware.example.com/k70');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('SSD 1TB NVMe', 'High-speed solid state drive for gaming and work', 479.00, 'https://hardware.example.com/ssd-1tb');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('IntelliJ IDEA Ultimate', 'The leading Java and Kotlin IDE', 199.90, 'https://software.example.com/intellij');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('Adobe Photoshop', 'Professional photo editing software', 129.90, 'https://software.example.com/photoshop');

INSERT INTO tb_category_product (product_id, category_id) VALUES (1, 1);
INSERT INTO tb_category_product (product_id, category_id) VALUES (1, 1);  -- A Storm of Swords → Books
INSERT INTO tb_category_product (product_id, category_id) VALUES (2, 1);  -- Clean Code → Books
INSERT INTO tb_category_product (product_id, category_id) VALUES (3, 1);  -- The Pragmatic Programmer → Books
INSERT INTO tb_category_product (product_id, category_id) VALUES (4, 2);  -- Batman: Year One → Comics
INSERT INTO tb_category_product (product_id, category_id) VALUES (5, 2);  -- Watchmen → Comics
INSERT INTO tb_category_product (product_id, category_id) VALUES (6, 3);  -- Mechanical Keyboard → Hardware
INSERT INTO tb_category_product (product_id, category_id) VALUES (7, 3);  -- SSD 1TB → Hardware
INSERT INTO tb_category_product (product_id, category_id) VALUES (8, 4);  -- IntelliJ IDEA → Software
INSERT INTO tb_category_product (product_id, category_id) VALUES (9, 4);  -- Adobe Photoshop → Software


CREATE INDEX idx_cat_id ON tb_category (id);
CREATE INDEX idx_cat_name ON tb_category (name);
CREATE INDEX idx_prod_id ON tb_product (id);