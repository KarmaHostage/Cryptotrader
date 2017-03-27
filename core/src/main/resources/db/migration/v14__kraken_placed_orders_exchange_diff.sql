rename table kraken_placed_orders to user_trades;

alter table user_trades
 add column exchange varchar(20) NOT NULL;