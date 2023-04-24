create table IF NOT EXISTS orders(
    id serial primary key not null,
    dish varchar(2000)
);