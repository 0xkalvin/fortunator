CREATE TABLE transaction_categories (
	id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT NOT NULL,
	name varchar(255) NOT NULL,
	description varchar(255) NOT NULL,

	primary key (id)
);

alter table transaction_categories add constraint fk_user
foreign key (user_id) references users (id);
