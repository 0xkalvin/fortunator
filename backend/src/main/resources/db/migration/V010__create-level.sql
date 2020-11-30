CREATE TABLE level (
	id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT NOT NULL,
	level INT NOT NULL,
	level_name varchar(50) NOT NULL,
	level_score decimal(10,2) NOT NULL,
	max_level_score decimal(10, 2) NOT NULL,

	primary key (id),
	UNIQUE(user_id)
);

alter table level add constraint fk_user 
foreign key (user_id) references users(id)
