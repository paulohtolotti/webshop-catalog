INSERT INTO tb_category (name) VALUES ('Books');
INSERT INTO tb_category (name) VALUES  ('Comics');
INSERT INTO tb_category (name) VALUES  ('Hardware');
INSERT INTO tb_category (name) VALUES  ('Software');


INSERT INTO tb_product (name, description, price, img_url) VALUES ('A storm of swords', 'A song of ice and fire', 65.70, 'https://rrmartin.com/storm');

INSERT INTO tb_category_product (category_id, product_id) VALUES (1, 1);
