create table crypto
(
    id          bigint  not null primary key,
    create_date timestamp(6) with time zone,
    update_date timestamp(6) with time zone,
    name        varchar(255),
    active      boolean not null
);

alter table crypto owner to postgres;


create table price
(
    id              bigint                      not null primary key,
    create_date     timestamp(6) with time zone,
    update_date     timestamp(6) with time zone,
    price           numeric(38, 2)              not null,
    price_timestamp timestamp(6) with time zone not null,
    crypto_id       bigint
        constraint crypto_key references crypto
);

alter table price owner to postgres;


CREATE SEQUENCE price_id_seq
    INCREMENT 1
START 1;

CREATE SEQUENCE crypto_id_seq
    INCREMENT 1
START 1;
