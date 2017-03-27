alter table kraken_general_advice
ADD COLUMN exchange varchar(20) NOT NULL;

rename table kraken_general_advice to general_advice;