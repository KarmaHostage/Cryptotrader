create table kraken_trades(
      id bigint(20) NOT NULL AUTO_INCREMENT,
      price DOUBLE NOT NULL,
      trade_time TIMSTAMP NOT NULL,
      tradable_amount DOUBLE NOT NULL,
      currency_pair VARCHAR(10) NOT NULL,
      primary key(id)
)