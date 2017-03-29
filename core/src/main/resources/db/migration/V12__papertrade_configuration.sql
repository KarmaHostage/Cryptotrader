create table papertrade_configurations(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    currency_pair varchar(10) NOT NULL,
    strategy_period varchar(30) NOT NULL,
    strategy_type varchar(10) NOT NULL,
    strategy_advice varchar(10) NOT NULL,
    creation_time timestamp not null,
    exchange varchar(20) NOT NULL,
    active boolean default true,
    user_id bigint(20) not null,
    constraint fk_ptconfig_user FOREIGN KEY (user_id) references cryptotrader_user(id)
);