create table public.productos
(
    id          serial
        primary key,
    cantidad    integer not null,
    nombre      varchar(100),
    tipo        varchar(40),
    valor       integer not null,
    valor_final integer not null
);

alter table public.productos
    owner to postgres;

