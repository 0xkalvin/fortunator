CREATE TABLE transactions (
	id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT NOT NULL,
	transaction_category_id INT NOT NULL,
	type varchar(20) NOT NULL,
	description varchar(255),
	amount decimal(10,2) NOT NULL,
	date date,
	primary key (id)
);

alter table transactions add constraint fk_user
foreign key (user_id) references users (id);

alter table transactions add constraint fk_transaction_category
foreign key (transaction_category_id) references transaction_categories (id);