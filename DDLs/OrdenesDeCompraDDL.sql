create table public.lista_producto
(
    id              serial
        primary key,
    cantidad        integer not null,
    id_oc_cliente   integer not null,
    id_oc_proveedor integer not null,
    id_producto     integer not null,
    valor_pago      integer not null
);

alter table public.lista_producto
    owner to postgres;

create table public.ordenes_de_compra_cliente
(
    id                serial
        primary key,
    empresa_despacho  varchar(30),
    estado_entrega    varchar(13),
    estado_factura    varchar(11),
    estado_pago       varchar(10),
    fecha_inicio_pago date,
    fecha_pago        date,
    fecha_solicitud   date,
    id_cliente        varchar(13),
    modo_pago         varchar(14),
    numero_cheque     integer not null,
    numero_factura    integer not null,
    tiempo_de_pago    integer not null,
    valor_pago        integer not null
);

alter table public.ordenes_de_compra_cliente
    owner to postgres;

create table public.ordenes_de_compra_proveedor
(
    id            serial
        primary key,
    estado_pago   varchar(10),
    factura       varchar(255),
    fecha_entrega date,
    fecha_pago    date,
    id_proveedor  integer not null,
    valor_pago    integer not null
);

alter table public.ordenes_de_compra_proveedor
    owner to postgres;

