
create table message_template
(
    id   varchar(100)  not null primary key,
    fields jsonb not null
);

create table message
(
    id   varchar(100)  not null primary key,
    template varchar(100)  not null  references message_template(id),
    content jsonb  not null
);
