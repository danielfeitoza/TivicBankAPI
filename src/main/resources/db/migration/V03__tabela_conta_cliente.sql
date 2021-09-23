CREATE TABLE conta (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	conta VARCHAR(20) NOT NULL,
	saldo VARCHAR(20) NOT NULL,
	tipo  VARCHAR(35) NOT NULL,
	agencia VARCHAR(8) NOT NULL,
	id_cliente BIGINT(20) NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES cliente (id),
	id_banco BIGINT(20)  NOT NULL,
	FOREIGN KEY (id_banco)   REFERENCES banco (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO conta (conta, saldo, tipo, agencia, id_cliente, id_banco)
VALUES ('1700-01', '5.320,50', 'CONTA CORRENTE', '3566', 1, 1);