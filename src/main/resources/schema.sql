DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id integer primary key,
    username varchar(100) not null,
    email varchar(200) not null,
    password varchar(200) not null
);