CREATE TABLE IF NOT EXISTS account (
    id_account serial primary key,
    document_number text not null,
    status text not null,
    balance decimal not null,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS operation_type (
   id_operation_type serial primary key,
   description text not null,
   created_on TIMESTAMP NOT NULL,
   updated_on TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS transaction (
   id_transaction serial primary key,
   id_operation_type int not null,
   id_account int not null,
   amount decimal not null,
   status text not null,
   created_on TIMESTAMP NOT NULL,
   updated_on TIMESTAMP NULL,
   foreign key (id_operation_type) references operation_type (id_operation_type),
   foreign key (id_account) references account (id_account)
);

insert into operation_type (description, created_on) values ('COMPRA A VISTA', now());
insert into operation_type (description, created_on) values ('COMPRA PARCELADA', now());
insert into operation_type (description, created_on) values ('SAQUE', now());
insert into operation_type (description, created_on) values ('PAGAMENTO', now());