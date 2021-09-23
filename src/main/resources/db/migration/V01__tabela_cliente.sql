CREATE TABLE cliente (
	id BIGINT(20)  PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(30) NOT NULL,
	dataNasc DATETIME,
	cpf VARCHAR(11) NOT NULL,
	email VARCHAR(40) NOT NULL,
	senha VARCHAR(100) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cliente (nome, dataNasc, cpf, email, senha)
VALUES ("Fulano Silva", "2001-09-01", "99988877721", "fulano@gmail.com","qwert123");