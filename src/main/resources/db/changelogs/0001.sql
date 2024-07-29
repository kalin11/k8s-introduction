--liquibase formatted sql

--changeset a.k.lysenko:1 logicalFilePath:db/changelogs/0001.sql

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT CHECK ( length(name) > 0 ) NOT NULL,
    surname TEXT CHECK ( length(surname) > 0 ) NOT NULL
);

CREATE TABLE posts (
    id UUID PRIMARY KEY ,
    content TEXT CHECK ( length(content) > 0 ) NOT NULL,
    likes INT NOT NULL DEFAULT 0,
    dislikes INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP
);