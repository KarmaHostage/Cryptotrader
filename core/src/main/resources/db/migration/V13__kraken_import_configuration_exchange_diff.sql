rename table kraken_import_configuration to import_configuration;

alter table import_configuration
 add column exchange varchar(20) NOT NULL;