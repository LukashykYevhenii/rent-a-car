CREATE DATABASE IF NOT EXISTS rent_a_car;

USE rent_a_car;

CREATE TABLE IF NOT EXISTS car
(
    `id_car`      INT            NOT NULL AUTO_INCREMENT,
    `brand`       VARCHAR(100)   NOT NULL,
    `model`       VARCHAR(100)   NOT NULL,
    `color`       VARCHAR(100)   NOT NULL,
    `year`        VARCHAR(100)   NOT NULL,
    `reg_number`  VARCHAR(10)    NOT NULL,
    `rentalPrice` DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id_car`)
);


CREATE TABLE images
(
    `id_images`    INT           NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(1000) NOT NULL,
    `content_type` VARCHAR(50),
    `content`      LONGBLOB      NOT NULL,
    PRIMARY KEY (`id_images`)
);

CREATE TABLE orders
(
    `id_order`    INT            NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `start_rent`  DATE           NOT NULL,
    `end_rent`    DATE           NOT NULL,
    `total_price` DECIMAL(10, 2) NOT NULL,
    `is_approved` BIT            NOT NULL,
    `is_damages`  BIT            NOT NULL,
    `is_returned` BIT            NOT NULL,
    `is_paid`     BIT            NOT NULL
);

CREATE TABLE client
(
    id_client  INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    sur_name   VARCHAR(255) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    phone      VARCHAR(20)
);

CREATE TABLE damages
(
    id_damages   INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description  VARCHAR(1000) NOT NULL,
    repair_price decimal(10, 2),
    order_id     INT           NOT NULL
);

CREATE TABLE payment
(
    id_payment   INT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    payment_date DATE           NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL,
    order_id     INT            NOT NULL
);

CREATE TABLE admin
(
    id_admin INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(150) NOT NULL
);