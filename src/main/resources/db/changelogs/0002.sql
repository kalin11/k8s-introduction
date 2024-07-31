--liquibase formatted sql

--changeset a.k.lysenko:2 logicalFilePath:db/changelogs/0002.sql

ALTER TABLE posts
    ADD COLUMN user_id UUID NOT NULL REFERENCES users(id);
