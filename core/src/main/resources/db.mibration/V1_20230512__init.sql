CREATE TABLE IF NOT EXISTS account (
    id_account serial primary key,
    document_number text not null,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP NOT NULL
);