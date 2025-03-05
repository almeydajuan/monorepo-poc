# TODO: automatise imports of migrations
CREATE TABLE user_schema.users
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INTEGER      NOT NULL CHECK (age >= 16)
);
