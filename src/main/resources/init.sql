CREATE SCHEMA IF NOT EXISTS db_tBot_passManager;
USE db_tBot_passManager;

CREATE TABLE db_pass (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name_service VARCHAR(30),
        login VARCHAR(30),
        password VARCHAR(100)
        );