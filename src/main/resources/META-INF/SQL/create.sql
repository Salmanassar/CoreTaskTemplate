CREATE DATABASE testbase;
USE testbase;
set global time_zone = '+1:00';
CREATE TABLE user
(
    id       int auto_increment primary key,
    name     varchar(45) NOT NULL,
    lastName varchar(45) NOT NULL,
    age      int         NOT NULL
);