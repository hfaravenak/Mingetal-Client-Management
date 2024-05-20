create table public.contacto
(
    rut       varchar(13) not null
        primary key,
    correo    varchar(100),
    fono_cel  varchar(18),
    fono_fijo varchar(18),
    nombre    varchar(100)
);

alter table public.contacto
    owner to postgres;

create table public.proveedor
(
    id_proveedor serial
        primary key,
    comentario   varchar(255),
    empresa      varchar(30),
    id_contacto  varchar(13),
    id_contacto2 varchar(13),
    id_contacto3 varchar(13),
    rubro        varchar(30)
);

alter table public.proveedor
    owner to postgres;
