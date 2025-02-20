CREATE TABLE cinemas (
    id SERIAL,
    name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
