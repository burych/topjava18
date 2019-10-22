DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT into meals (user_id, datetime, description, calories) VALUES
   (100001, '2015-05-30 10:00:00', 'завтрак', 300),
   (100000, '2015-05-30 10:00:00', 'завтрак', 200),
   (100001, '2015-05-30 12:00:00', 'обед', 500),
   (100000, '2015-05-30 12:00:00', 'обед', 500);

