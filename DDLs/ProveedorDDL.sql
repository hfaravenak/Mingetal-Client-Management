CREATE DATABASE proveedor
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

create table public.contacto
(
    id_contacto serial
        primary key,
    correo      varchar(255),
    fono_fijo   varchar(255),
    foro_cel    varchar(255),
    nombre      varchar(255),
    rut         varchar(255)
);

alter table public.contacto
    owner to postgres;

create table public.proveedor
(
    id_proveedor serial
        primary key,
    comentario   varchar(255),
    empresa      varchar(255),
    id_contacto  integer not null,
    id_contacto2 integer not null,
    id_contacto3 integer not null,
    rubro        varchar(255)
);

alter table public.proveedor
    owner to postgres;

