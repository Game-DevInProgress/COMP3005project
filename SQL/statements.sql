CREATE TABLE IF NOT EXISTS Books(
    title VARCHAR(30) NOT NULL,
    ISBN INT NOT NULL PRIMARY KEY,
    pages INT NOT NULL,
    price FLOAT NOT NULL,
    genre VARCHAR(30) NOT NULL,
    author VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS User(
    fullname VARCHAR(50) NOT NULL,
    userAddress VARCHAR(30),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    accountType CHAR(1) 
);

CREATE TABLE IF NOT EXISTS Bookstore(
    inventory text[][] NOT NULL,
    ISBN INT NOT NULL,
    genres text[] NOT NULL,
    categories text[] NOT NULL, 
    authors text[] NOT NULL PRIMARY KEY,
    orders text[] NOT NULL
);

CREATE TABLE IF NOT EXISTS Order(
    id INT NOT NULL PRIMARY KEY,
    books text[] NOT NULL
);
