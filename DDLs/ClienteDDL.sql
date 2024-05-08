CREATE DATABASE cliente
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

create table public.cliente
(
    rut      varchar(255) not null
        primary key,
    email    varchar(255),
    empresa  varchar(255),
    nombre   varchar(255),
    telefono varchar(255)
);

alter table public.cliente
    owner to postgres;

create table public.cotizacion
(
    id_cotizacion serial
        primary key,
    estado        varchar(255),
    fecha         date,
    pedido        varchar(255),
    rut_cliente   varchar(255)
);

alter table public.cotizacion
    owner to postgres;

