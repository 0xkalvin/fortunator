CREATE TABLE USERS (
	id INT GENERATED ALWAYS AS IDENTITY,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	primary key (id)
);

