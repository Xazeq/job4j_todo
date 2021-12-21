CREATE TABLE if NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar NOT NULL UNIQUE,
    email varchar NOT NULL UNIQUE,
    password varchar NOT NULL UNIQUE
);

CREATE TABLE if NOT EXISTS items
(
    id SERIAL PRIMARY KEY,
    description text,
    created timestamp,
    isDone boolean,
    user_id int REFERENCES users(id)
);