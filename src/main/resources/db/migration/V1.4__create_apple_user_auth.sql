create table apple_users_auth
(
	apple_user_identifier VARCHAR(250) not null
		constraint apple_users_auth_pk
			primary key,
	user_id bigint not null
		constraint apple_users_auth_users_id_fk
			references users,
	state varchar(200),
	authorized_scopes jsonb not null,
	authorization_code jsonb not null,
	identity_token varchar(200) not null,
	email varchar(300) not null,
	first_name varchar(200) not null,
	last_name varchar(200) not null,
	real_user_status varchar(200) not null
);