alter table kraken_placed_orders
  add column currency_pair VARCHAR(10) DEFAULT 'ETH/EUR';

alter table kraken_placed_orders
  add column user_id bigint DEFAULT NULL;

alter table kraken_placed_orders
  add constraint fk_placed_orders_user foreign key (user_id) references cryptotrader_user(id)
