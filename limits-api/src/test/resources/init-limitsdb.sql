create table account_limits_entity
(
    id             bigint           not null
        primary key,
    account_number varchar(255)     not null,
    max            double precision not null,
    min            double precision not null
);

alter table account_limits_entity
    owner to "user";

insert into account_limits_entity (id, account_number, min, max) values (1, '000000001', 10.00, 10000.00);
insert into account_limits_entity (id, account_number, min, max) values (2, '000000002', 00.00, 100000.00);
insert into account_limits_entity (id, account_number, min, max) values (3, '000000003', 100.00, 1000000.00);
insert into account_limits_entity (id, account_number, min, max) values (4, '000000004', 00.00, 5000.00);