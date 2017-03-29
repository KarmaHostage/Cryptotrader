create table paper_trades (
  	id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    price double not null,
    amount double not null,
    order_type varchar(10) NOT NULL,
    placed_at timestamp not null,
    papertrade_config_id bigint NOT NULL,
    constraint fk_paper_trades_ptconfig_id foreign key (papertrade_config_id) references papertrade_configurations(id)
);