CREATE TABLE TRANSACTION_CATEGORIES (
	id INT GENERATED ALWAYS AS IDENTITY,
	name varchar(255) NOT NULL,
	description varchar(255),
	primary key (id)
);

INSERT INTO TRANSACTION_CATEGORIES (name, description) VALUES ('Salário', 'Salário mensal');
INSERT INTO TRANSACTION_CATEGORIES (name, description) VALUES ('Contas', 'Contas da casa');