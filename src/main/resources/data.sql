-- Seed the DB during the application bootstrap with some dummy data for testing.

-- Passwords encryped using BCrypt encoding.
INSERT INTO user(username, password) VALUES ('basic_user', '$2a$10$TjW6DQ8DZuJ9RBnJKYfc4eYB2wwHJ/K5Ni7FUIgot.Gc2jy8Jj6RS');
INSERT INTO user(username, password) VALUES ('admin_user', '$2a$10$e7C8VB7GztkEPhUv6l5Xt.1YguWgkhgG11mHha4jtUiEdO82.ye7y');

INSERT INTO role(name) VALUES ('USER');
INSERT INTO role(name) VALUES ('ADMIN');

INSERT INTO users_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles(user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles(user_id, role_id) VALUES (2, 2);

INSERT INTO task(name, description, user_id) VALUES ('Send Email', 'Send email to manager before COB', 1);
INSERT INTO task(name, description, user_id, completed) VALUES ('Dry-cleaning', 'Pickup dry-cleaning before leaving office', 1, TRUE);
INSERT INTO task(name, description, user_id) VALUES ('Gym', 'Remember to go to the gym', 2)
