INSERT INTO user (name, date_of_birth, password)
VALUES ('John', '1985-05-15', 'password123');
INSERT INTO user (name, date_of_birth, password)
VALUES ('Jane', '1990-11-22', 'securepass');
INSERT INTO user (name, date_of_birth, password)
VALUES ('Alice', '1978-03-30', 'alicepwd');
INSERT INTO user (name, date_of_birth, password)
VALUES ('Bob', '2000-07-07', 'bobsecure');
INSERT INTO user (name, date_of_birth, password)
VALUES ('Charlie', '1995-01-01', 'charlie123');

INSERT INTO accounts (user_id, balance, initial_balance)
VALUES (1, 1743, 1743);
INSERT INTO accounts (user_id, balance, initial_balance)
VALUES (2, 376, 376);
INSERT INTO accounts (user_id, balance, initial_balance)
VALUES (3, 100000, 100000);
INSERT INTO accounts (user_id, balance, initial_balance)
VALUES (4, 500, 500);
INSERT INTO accounts (user_id, balance, initial_balance)
VALUES (5, 2500, 2500);

INSERT INTO email_data (user_id, email)
VALUES (4, 'user4@example.com');
INSERT INTO email_data (user_id, email)
VALUES (5, 'user5@example.com');
INSERT INTO email_data (user_id, email)
VALUES (6, 'user6@example.com');
INSERT INTO email_data (user_id, email)
VALUES (7, 'user7@example.com');
INSERT INTO email_data (user_id, email)
VALUES (8, 'user8@example.com');


INSERT INTO phone_data (user_id, phone)
VALUES (4, '89123456789');
INSERT INTO phone_data (user_id, phone)
VALUES (5, '89234567890');
INSERT INTO phone_data (user_id, phone)
VALUES (6, '89345678901');
INSERT INTO phone_data (user_id, phone)
VALUES (7, '89456789012');
INSERT INTO phone_data (user_id, phone)
VALUES (8, '89567890123');
