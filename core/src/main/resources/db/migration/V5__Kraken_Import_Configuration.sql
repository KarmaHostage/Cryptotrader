create table kraken_import_configuration(
        id bigint(20) NOT NULL AUTO_INCREMENT,
        last_import_timestamp TIMESTAMP DEFAULT NULL,
        last_import_id NUMBER DEFAULT NULL,
        bid_price DOUBLE DEFAULT NULL,
        ask_price DOUBLE DEFAULT NULL,
        price DOUBLE DEFAULT NULL,
        primary key(id)
);