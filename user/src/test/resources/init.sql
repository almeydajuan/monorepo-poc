-- TODO: this should be done via a bash script on initialisation
CREATE SCHEMA IF NOT EXISTS user_schema;

-- TODO: this migration will be removed
CREATE TABLE user_schema.users
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INTEGER      NOT NULL CHECK (age >= 16)
);
