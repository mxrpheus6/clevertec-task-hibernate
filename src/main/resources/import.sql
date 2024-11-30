INSERT INTO category (id, name) VALUES (1, 'Sedan');
INSERT INTO category (id, name) VALUES (2, 'SUV');
INSERT INTO category (id, name) VALUES (3, 'Hatchback');
INSERT INTO category (id, name) VALUES (4, 'Coupe');
INSERT INTO category (id, name) VALUES (5, 'Convertible');

INSERT INTO car_showroom (id, name, address) VALUES (1, 'Luxury Cars', 'Downtown Street, 10');
INSERT INTO car_showroom (id, name, address) VALUES (2, 'Budget Auto', 'Main Avenue, 5');
INSERT INTO car_showroom (id, name, address) VALUES (3, 'Premium Motors', 'Sunset Blvd, 20');

INSERT INTO car (id, brand, model, manufacture_year, price, category_id, showroom_id) VALUES (1, 'BMW', 'X5', 2022, 80000, 2, 1);
INSERT INTO car (id, brand, model, manufacture_year, price, category_id, showroom_id) VALUES (2, 'Toyota', 'Corolla', 2020, 20000, 1, 2);
INSERT INTO car (id, brand, model, manufacture_year, price, category_id, showroom_id) VALUES (3, 'Audi', 'Q7', 2021, 75000, 2, 1);
INSERT INTO car (id, brand, model, manufacture_year, price, category_id, showroom_id) VALUES (4, 'Ford', 'Focus', 2019, 18000, 3, 2);
INSERT INTO car (id, brand, model, manufacture_year, price, category_id, showroom_id) VALUES (5, 'Mercedes-Benz', 'C-Class', 2023, 85000, 1, 3);

INSERT INTO client (id, name, registration_date) VALUES (1, 'John Doe', '2023-01-15');
INSERT INTO client (id, name, registration_date) VALUES (2, 'Jane Smith', '2023-02-10');
INSERT INTO client (id, name, registration_date) VALUES (3, 'Mike Johnson', '2023-03-05');
INSERT INTO client (id, name, registration_date) VALUES (4, 'Anna Brown', '2023-04-20');
INSERT INTO client (id, name, registration_date) VALUES (5, 'Chris Green', '2023-05-30');

INSERT INTO review (id, text, rating, car_id, client_id) VALUES (1, 'Excellent car!', 5, 1, 1);
INSERT INTO review (id, text, rating, car_id, client_id) VALUES (2, 'Very reliable.', 4, 2, 2);
INSERT INTO review (id, text, rating, car_id, client_id) VALUES (3, 'Good value for the price.', 4, 4, 3);
INSERT INTO review (id, text, rating, car_id, client_id) VALUES (4, 'Luxurious and comfortable.', 5, 3, 4);
INSERT INTO review (id, text, rating, car_id, client_id) VALUES (5, 'Amazing performance.', 5, 5, 5);

INSERT INTO client_cars (car_id, client_id) VALUES (1, 1);
INSERT INTO client_cars (car_id, client_id) VALUES (2, 2);
INSERT INTO client_cars (car_id, client_id) VALUES (3, 4);
INSERT INTO client_cars (car_id, client_id) VALUES (4, 3);
INSERT INTO client_cars (car_id, client_id) VALUES (5, 5);

INSERT INTO client_contacts (client_id, contact) VALUES (1, 'chillguy@gmail.com');
INSERT INTO client_contacts (client_id, contact) VALUES (1, '+375291111111');
INSERT INTO client_contacts (client_id, contact) VALUES (2, '+375292222222');
INSERT INTO client_contacts (client_id, contact) VALUES (3, '+375293333333');
INSERT INTO client_contacts (client_id, contact) VALUES (3, 'vova@gmail.com');
INSERT INTO client_contacts (client_id, contact) VALUES (4, 'four@gmail.com');
INSERT INTO client_contacts (client_id, contact) VALUES (5, 'five@gmail.com');

-- Необходимо обновить последовательности т.к. тут мы устанавливали id вручную
-- Без этого Hibernate устанавливает id новым сущностям начиная с 1
SELECT setval('car_id_seq', (SELECT MAX(id) FROM car));
SELECT setval('category_id_seq', (SELECT MAX(id) FROM category));
SELECT setval('car_showroom_id_seq', (SELECT MAX(id) FROM car_showroom));
SELECT setval('client_id_seq', (SELECT MAX(id) FROM client));
SELECT setval('review_id_seq', (SELECT MAX(id) FROM review));