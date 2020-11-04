CREATE TABLE goals (
	amount decimal(10,2),
	date date NOT NULL,
	description varchar(255) NOT NULL,
	id INT GENERATED ALWAYS AS IDENTITY,
	status varchar(50) NOT NULL,
	score decimal(10,2) NOT NULL,
	type varchar(50) NOT NULL,
	user_id INT NOT NULL,

	primary key (id)
);

alter table goals add constraint fk_user foreign key (user_id) references users (id);
