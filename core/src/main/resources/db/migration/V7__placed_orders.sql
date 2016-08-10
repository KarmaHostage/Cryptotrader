create table kraken_placed_orders(
  	id BIGINT  NOT NULL PRIMARY KEY ,
    price double not null,
    amount double not null,
    order_type varchar(10) NOT NULL,
    is_closed bool default false,
    placed_at timestamp not null
);