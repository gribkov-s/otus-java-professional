
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

create table parameters_template
(
    id   varchar(100)  not null primary key,
    fields jsonb not null
);

create table parameters
(
    id   varchar(100)  not null primary key,
    template varchar(100)  not null  references parameters_template(id),
    content jsonb  not null
);

create table connection_template
(
    id   varchar(100)  not null primary key,
    fields jsonb not null
);

create table "connection"
(
    id   varchar(100)  not null primary key,
    template varchar(100)  not null  references connection_template(id),
    content jsonb  not null
);

create table task_template
(
    id   varchar(100)  not null primary key,
    task_type varchar(100),
    "connection" varchar(100)  not null  references "connection"(id),
    parameters_template varchar(100)  references parameters_template(id),
    message_template varchar(100)  references message_template(id)
);

