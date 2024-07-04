--liquibase formatted sql
--changeset NeronKid:1

CREATE TABLE wallet (
    wallet_id UUID PRIMARY KEY NOT NULL,
    balance INTEGER,
    operation_type VARCHAR(250)
);
