create table role (
  id int(11) NOT NULL,
  authority varchar(25) NOT NULL,
  primary key(id)
);

create table cryptotrader_user(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(200) NOT NULL,
  password varchar(100) DEFAULT NULL,
  account_non_expired bool DEFAULT TRUE,
  account_non_locked bool DEFAULT TRUE,
  credentials_non_expired bool DEFAULT TRUE,
  enabled bool DEFAULT TRUE,
  email varchar(200) NOT NULL,
  activationcode VARCHAR(40) DEFAULT NULL,
  password_reset_code VARCHAR(40) DEFAULT NULL,
  primary key(id)
);

alter table cryptotrader_user
add unique key(email);

alter table cryptotrader_user
add unique key(username);


create table user_role(
  user_id bigint(20),
  role_id int(11)
);

alter table user_role
  ADD CONSTRAINT FK_user_role_user foreign key (user_id) references cryptotrader_user(id);

alter table user_role
  ADD CONSTRAINT FK_user_role_role foreign key (role_id) references role(id);

alter table user_role
  ADD UNIQUE KEY UK_user_role(user_id, role_id);
