create sequence transaction_entity_seq
    increment by 50;

alter sequence transaction_entity_seq owner to "user";

create table transaction_entity
(
    id                  bigint           not null
        primary key,
    amount              double precision not null,
    from_account_number varchar(255)     not null,
    to_account_number   varchar(255)     not null
);

alter table transaction_entity
    owner to "user";
