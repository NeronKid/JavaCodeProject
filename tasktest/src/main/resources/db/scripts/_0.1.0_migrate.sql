--liquibase formatted sql
--changeset NeronKid:1

CREATE TABLE IF NOT EXISTS wallet (
    wallet_id UUID PRIMARY KEY NOT NULL,
    balance INTEGER NOT NULL,
    operation_type VARCHAR(250)
);
