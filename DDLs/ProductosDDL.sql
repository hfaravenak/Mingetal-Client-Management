CREATE DATABASE productos
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

create table public.productos
(
    id          serial
        primary key,
    cantidad    integer not null,
    nombre      varchar(255),
    tipo        varchar(255),
    valor       integer not null,
    valor_final integer not null
);

alter table public.productos
    owner to postgres;

