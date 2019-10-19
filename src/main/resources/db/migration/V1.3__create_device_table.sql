create table device
(
	-- Only integer types can be auto increment
	id BIGSERIAL not null
		constraint device_pk
			primary key,
	user_id bigint not null
		constraint device_users_id_fk
			references users,
	serial_number varchar(250),
	device_os VARCHAR(100) not null,
	os_version varchar(100) not null,
	model varchar(100) not null,
	language varchar(100) not null,
	push_notification boolean not null,
	push_token varchar(250) not null
);