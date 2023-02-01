--Таблица пользователей
create table users
(
    id       serial primary key,
    email    varchar not null unique,
    name     varchar not null,
    password varchar not null
);