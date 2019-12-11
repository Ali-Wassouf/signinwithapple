create table users
(
	id bigserial not null,
	username varchar(200) not null
);

create unique index users_id_uindex
	on users (id);

alter table users
	add constraint users_pk
		primary key (id);

