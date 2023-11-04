
create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50) not null
);

create table address(
    id   bigserial not null primary key,
    street varchar(50) not null,
    client_id bigint not null references client(id)
);

create table phone
(
    id   bigserial not null primary key,
    number varchar(50) not null,
    client_id bigint not null references client(id)
);
