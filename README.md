# TivicBankAPI
TivicBankAPI - API para a comunicação com o banco MySQL

API feita com o Spring Boot, conectanto a um banco MySQL local,
o banco é gerado automaticamente, criando as tabelas Cliente, 
Banco, Conta, Saque e Deposito.
As tabelas Cliente, Banco e Conta são povoadas com o registro
cada, as demais de acordo com as requisições feitas pela 
aplicação front-end serão geradas as informações.

Talvez seja necessario alterar o usuário e o password do
banco no arquivo application.properties dentro do spring.
