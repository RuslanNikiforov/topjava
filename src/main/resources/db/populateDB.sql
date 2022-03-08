DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE globalmeal_seq RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, description, calories)
VALUES

       (100000, 'Food1', 820);
SELECT pg_sleep(1);

INSERT INTO meals (user_id, description, calories)
VALUES

    (100001, 'Food2', 500);

SELECT pg_sleep(1);

INSERT INTO meals (user_id, description, calories)
VALUES

    (100001, 'Food3', 400);

INSERT INTO meals (user_id, description, datetime, calories)
VALUES

    (100000, 'Food4', '2022-02-24 00:00' , 300);