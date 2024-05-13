create table public.cliente
(
    rut      varchar(14) not null
        primary key,
    email    varchar(100),
    empresa  varchar(30),
    nombre   varchar(100),
    telefono varchar(13)
);

alter table public.cliente
    owner to postgres;

create table public.cotizacion
(
    id_cotizacion serial
        primary key,
    estado        varchar(10),
    fecha         date,
    pedido        varchar(100),
    rut_cliente   varchar(13)
);

alter table public.cotizacion
    owner to postgres;
