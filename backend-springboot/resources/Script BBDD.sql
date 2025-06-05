-- Crear la base de datos
CREATE DATABASE BasketTrainer;
USE BasketTrainer;

-- Tabla TRAINER
CREATE TABLE trainer (
    trainer_dni VARCHAR(9) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    first_surname VARCHAR(50) NOT NULL,
    second_surname VARCHAR(50),
    birthdate DATE,
    e_mail VARCHAR(100),
    telephone VARCHAR(15)
);

-- Tabla TEAM
CREATE TABLE team (
    team_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    league VARCHAR(100),
    trainer_dni VARCHAR(9),
    FOREIGN KEY (trainer_dni) REFERENCES trainer(trainer_dni) ON DELETE SET NULL
);

-- Tabla PLAYER
CREATE TABLE player (
    player_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    first_surname VARCHAR(50) NOT NULL,
    second_surname VARCHAR(50),
    birthdate DATE,
    e_mail VARCHAR(100),
    telephone VARCHAR(15),
    category VARCHAR(50),
    trainer_dni VARCHAR(9),
    team_id BIGINT,
    FOREIGN KEY (trainer_dni) REFERENCES trainer(trainer_dni) ON DELETE SET NULL,
    FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE SET NULL
);
