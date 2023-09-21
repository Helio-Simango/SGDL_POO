-- Sistema de Gestao de Lanchonete
-- Script da criação da Base de dados
-- CopyRigtht (c)  2023 Helio_Simango, Kirov Mabasso
 -- drop database SGDL_POO;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

create database SGDL_POO DEFAULT CHARACTER SET utf8;
use SGDL_POO;

create table enderecoEntrega(
idEnderecoEntrega int not null auto_increment,
endereco text(100) not null,
primary key(idEnderecoEntrega)
) ENGINE = InnoDB ;

create table if not exists cliente (
idCliente int primary key auto_increment,
primeiroNome varchar(40) not null,
apelido varchar(40) not null,
email VARCHAR(255) not null unique,
senha varchar(40) not null,
dataNascimento Date,
sexo enum('M','F') not null,
dataCadastro timestamp Default current_timestamp,
estado varchar(40) not null,
enderecoResidencia varchar(40) not null,

-- Chaves Estrangeiras
fkEnderecoEntrega int,
constraint fk_endereco_entrega 
foreign key (fkEnderecoEntrega)  references 
enderecoEntrega (idEnderecoEntrega) ON DELETE cascade
)ENGINE = InnoDB;

-- Deletar a coluna "email" da tabela "cliente"
ALTER TABLE cliente DROP COLUMN email;

-- Adicionar uma nova coluna "cliente" com UNIQUE
ALTER TABLE cliente ADD COLUMN email VARCHAR(255) UNIQUE;

create table telefoneCliente(
idTelefoneCliente int primary key auto_increment,
contacto int,

-- Chaves Estrangeiras
fkCliente int,
constraint fk_telefone_cliente
foreign key (fkCliente) references
cliente (idCliente) ON delete cascade
)ENGINE = InnoDB;

-- --------------------------------------------------------------------------------------------
-- ------------------------------ EMPREGADO -------------------------------------------------

create table if not exists funcaoEmpregado(
idFuncao int primary key auto_increment,
especialidade varchar(40) not null,
descricao varchar(60)
)ENGINE = InnoDB;

create table if not exists empregado(
idEmpregado int primary key auto_increment,
primeiroNome varchar(40) not null,
apelido varchar(40) not null,
email varchar(60) not null unique,
senha varchar(40) not null,
dataNascimento Date,
sexo enum('M','F') not null,
dataCadastro timestamp Default current_timestamp,
estado varchar(40) not null,
enderecoResidencia varchar(40),

-- chave Estrangeira
fkFuncaoEmpregado int,
constraint fk_funcao_empregado foreign key
(fkFuncaoEmpregado) references funcaoEmpregado (idFuncao)
)ENGINE = InnoDB;

-- Deletar a coluna "email" da tabela "empregado"
-- ALTER TABLE empregado DROP COLUMN email;

-- Adicionar uma nova coluna "email" com UNIQUE
-- ALTER TABLE empregado ADD COLUMN email VARCHAR(255) UNIQUE;

-- Para remover a restrição NOT NULL da coluna "email"
-- ALTER TABLE empregado MODIFY COLUMN email VARCHAR(255) NULL;

-- Em seguida, adicione a constraint UNIQUE à coluna "email"
-- ALTER TABLE empregado ADD CONSTRAINT email_unique UNIQUE (email);

create table if not exists telefone_funcionario(
id_telefone_cli int primary key auto_increment,
contacto int not null,

-- Chaves Estrangeiras
fkEmpregado int,
constraint fk_telefone_empregado
foreign key (fkEmpregado) references
empregado (idEmpregado) ON delete cascade
)ENGINE = InnoDB;

-- ----------------------------------------------------------------------------------------
-- ------------------------------ PEDOIDO -------------------------------------------------

create table if not exists pedido(
idPedido int primary key auto_increment,
dataPedido timestamp Default current_timestamp,
situacaoPedido varchar(30) not null,

-- chaves Estrangeiras
fkEmpregado int,
constraint fk_pedido_empregado 
foreign key (fkEmpregado) references
empregado(idEmpregado),

fkCliente int,
constraint fk_pedido_Cliente
foreign key (fkCliente) references
cliente (idCliente)
)ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------------
-- -------------------------------- ENCOMENDA ---------------------------------------------

create table if not exists Encomenda(
idEncomenda int primary key auto_increment,
situacao varchar(30) not null,
custoEntrega double(10,3) not null,

-- Chave estrangeira
fkCliente int,
constraint fk_Encomenda_Cliente
foreign key (fkCliente) references
cliente (idCliente)
)ENGINE = InnoDB;

-- ----------------------------------------------------------------------------------------
-- -------------------------------- ENCOMENDA ---------------------------------------------

create table if not exists venda(
idVenda int primary key auto_increment,
fkPedido int,
fkCliente int,
fkEncomenda int,
situacao varchar(30) not null,
dataVenda timestamp default current_timestamp not null,
montante double(10, 3),
trocos double(10, 3),
tipoDepagamento VARCHAR(255),
valorTotalVenda double(10,3) not null,
-- chaves estrangeiras
constraint fk_venda_pedido
foreign key (fkPedido) references
pedido (idPedido),

constraint fk_venda_cliente
foreign key (fkCliente) references
cliente (idCliente),

constraint fk_venda_encomenda
foreign key (fkEncomenda) references
Encomenda (idEncomenda)
)ENGINE = InnoDB;

-- Adicionar as colunas "montante", "trocos" e "tipoDepagamento" à tabela "vendas"
-- Alter table venda drop column montante;
-- Alter table venda drop column trocos;
-- Alter table venda drop column tipoDepagamento;
-- alter  table venda drop column valorTotalVenda;

-- alter table venda add column valorTotalVenda double(10,3);

-- ALTER TABLE venda
-- ADD COLUMN montante double(10, 3),
-- ADD COLUMN trocos double(10, 3),
-- ADD COLUMN tipoDepagamento VARCHAR(255);

describe venda;
-- ----------------------------------------------------------------------------------------
-- -------------------------------- ITENSCARDAPIO -----------------------------------------

create table if not exists itemCardapio(
idItemCardapio int primary key auto_increment,
nomeItem  varchar(30) not null,
precoUnitarioCardapio double(10,3) not null,
-- chaves estrangeiras
fkPedido int,
constraint fk_ItensCardapio_pedido
foreign key (fkPedido) references
pedido (idPedido),

fkEncomenda int,
constraint fk_ItensCardapio_encomenda
foreign key (fkEncomenda) references
Encomenda (idEncomenda)
)ENGINE = InnoDB;

-- ----------------------------------------------------------------------------------------
-- -------------------------------- CATEGORAPRODUTO ---------------------------------------
create table if not exists categoriaProduto(
idCategoriaProduto int primary key auto_increment,
categoria varchar(30) not null,
descricao varchar(30)
)ENGINE = InnoDB;

alter table categoriaProduto modify column descricao varchar(255);
-- --------------------------------------------------------------------------------
-- -------------------------------- PRODUTO ---------------------------------------
create table if not exists produto(
idProduto int primary key auto_increment,
fkItemCardapio int,
idCategoriaProduto int,
nomeProduto varchar(60) not null,
quantidadeProduto int not null,
precoUnitarioProduto double(10,3) not null,
precoCompraProduto double(10,3) not null,
dataCompraProduto Date not null,
dataValidadeProduto Date not null,

-- Chave Estrangeira
constraint fk_idItemCardapio_produto 
foreign key (fkItemCardapio) references
itemCardapio(idItemCardapio)
)ENGINE = InnoDB;

-- alter table produto add column precoUnitarioProduto double(10,3) not null;
-- alter table produto add column precoCompraProduto double(10,3) not null;

desc produto;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
