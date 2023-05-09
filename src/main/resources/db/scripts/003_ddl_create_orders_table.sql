create table IF NOT EXISTS orders(
    id serial primary key not null,
    created      timestamp without time zone default (now() at time zone 'utc'),
    dish varchar(2000),
    status int references status(id) default 1
);

comment on table orders is '������� ��������� �������';
comment on column orders.id is '������������� ������';
comment on column orders.created is '����� �������� ������ �� �����';
comment on column orders.dish is '������ ������';
comment on column orders.status is '������ ������';