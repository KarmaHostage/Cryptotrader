create table user_trade_advice(
    	id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY ,
      order_id BIGINT NOT NULL,
      strategy_type varchar(10) NOT NULL,
      should_exit bool default 0,
      advice_time TIMESTAMP NOT NULL,
      strategy_period varchar(15) NOT NULL,
      constraint fk_kraken_poa_k_po foreign key (order_id) references user_trades(id)
);
