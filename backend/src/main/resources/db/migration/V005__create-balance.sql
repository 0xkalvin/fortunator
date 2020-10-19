CREATE TABLE balance (
	id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT NOT NULL,
	amount decimal(10,2) NOT NULL,

	primary key (id),
	UNIQUE(user_id)
);

alter table balance add constraint fk_user 
foreign key (user_id) references users(id)