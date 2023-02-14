INSERT INTO product(id, name, price) values(1, 'gloves', 20.00);
INSERT INTO product(id, name, price) values(2, 'hat', 25.00);
INSERT INTO product(id, name, price) values(3, 'cap', 30.00);

INSERT INTO discount(id, code, type, applicability, discountValue, metadata) values(1, 'FLAT_50_OFF_EVERY_3RD_ORDER', 'FLAT', 'EVERY_NTH_ORDER', '{"EVERY_NTH_ORDER":3}')