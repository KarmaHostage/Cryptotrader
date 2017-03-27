create table import_configuration(
        id bigint(20) NOT NULL AUTO_INCREMENT,
        last_import_timestamp TIMESTAMP DEFAULT NULL,
        last_import_id BIGINT,
        bid_price DOUBLE,
        ask_price DOUBLE,
        price DOUBLE,
        currency_pair varchar(10),
        exchange varchar(20) NOT NULL,
        primary key(id)
);