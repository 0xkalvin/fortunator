CREATE TYPE goals_types AS ENUM ('budget');

CREATE TABLE goals (
	id INT GENERATED ALWAYS AS IDENTITY,
	amount decimal(10,2),
	created_at date NOT NULL,
	description varchar(255) NOT NULL,
	type goals_types NOT NULL,
	user_id INT NOT NULL,

	primary key (id),
	UNIQUE(user_id)
);

alter table goals add constraint fk_user foreign key (user_id) references users(id);
