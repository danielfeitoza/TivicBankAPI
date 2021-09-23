CREATE TABLE deposito(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_deposito VARCHAR(40) NOT NULL,
	valor_deposito  VARCHAR(25) NOT NULL,
	data_deposito   TIMESTAMP,
	id_conta        BIGINT(20) NOT NULL,
	FOREIGN KEY     (id_conta) REFERENCES conta (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;