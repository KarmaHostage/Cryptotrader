create table user_trades(
  	id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    price double not null,
    amount double not null,
    order_type varchar(10) NOT NULL,
    is_closed bool default false,
    placed_at timestamp not null,
    exchange varchar(20) NOT NULL,
    currency_pair VARCHAR(10) DEFAULT 'ETH/EUR',
    user_id bigint DEFAULT NULL,
    constraint fk_placed_orders_user foreign key (user_id) references cryptotrader_user(id)
);