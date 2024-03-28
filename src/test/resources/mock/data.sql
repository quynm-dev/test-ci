INSERT INTO users (username, password, name, email, age, created_at, updated_at)
VALUES ('john_doe', '$2a$10$GtMb09DCIR04AFZD3r/Wte8/kw/PGyYnkmG2r4n46Jjw7.1p4qif6', 'John Doe', 'john@example.com', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users (username, password, name, email, age, created_at, updated_at)
VALUES ('jane_smith', '$2a$10$GtMb09DCIR04AFZD3r/Wte8/kw/PGyYnkmG2r4n46Jjw7.1p4qif6', 'Jane Smith', 'jane@example.com', 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users (username, password, name, email, age, created_at, updated_at)
VALUES ('alex_brown', '$2a$10$GtMb09DCIR04AFZD3r/Wte8/kw/PGyYnkmG2r4n46Jjw7.1p4qif6', 'Alex Brown', 'alex@example.com', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO books (title, users, author, isbn, created_at, updated_at)
VALUES ('The Great Gatsby', 1, 'F. Scott Fitzgerald', '9780743273565', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO books (title, users, author, isbn, created_at, updated_at)
VALUES ('To Kill a Mockingbird', 2, 'Harper Lee', '9780061120084', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO books (title, users, author, isbn, created_at, updated_at)
VALUES ('1984', 3, 'George Orwell', '9780451524935', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO books (title, users, author, isbn, created_at, updated_at)
VALUES ('The Catcher in the Rye', 1, 'J.D. Salinger', '9780316769488', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO books (title, users, author, isbn, created_at, updated_at)
VALUES ('The Grapes of Wrath', 2, 'John Steinbeck', '9780143039433', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
