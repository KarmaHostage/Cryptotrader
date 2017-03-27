rename table kraken_trade to trade_history;

alter table trade_history
 add column exchange varchar(20) NOT NULL;