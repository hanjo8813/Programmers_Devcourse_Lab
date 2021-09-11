create table products
(
    product_id   binary(16) primary key,
    product_name varchar(20) not null,
    category     varchar(50) not null,
    price        bigint      not null,
    description  varchar(500) default null,
    created_at   datetime(6) not null,
    updated_at   datetime(6) default null
);


DELIMITER //

CREATE FUNCTION UUID_TO_BIN(uuid CHAR(36))
    RETURNS BINARY(16) DETERMINISTIC
BEGIN
RETURN UNHEX(CONCAT(REPLACE(uuid, '-', '')));
END; //

DELIMITER ;


