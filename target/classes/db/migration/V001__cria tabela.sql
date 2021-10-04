create table cliente (
	idCliente bigint not null auto_increment,
	nome varchar(100) not null,
	email varchar(100) not null,
	telefone varchar(20) not null,

	primary key (idCliente)
);