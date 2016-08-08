create table market_snapshot (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    currency_pair varchar(10) NOT NULL,
    exchange varchar(10) NOT NULL,
    market_timestamp TIMESTAMP NOT NULL,
    bid DOUBLE NOT NULL,
    ask DOUBLE NOT NULL,
    current DOUBLE NOT NULL,
    primary key(id)
)