alter table users
	add gender VARCHAR(100) not null;

alter table users
	add gender_of_interest VARCHAR(100) not null;

alter table users
	add date_of_birth date not null;

alter table users
	add country_code VARCHAR(100) not null;

alter table users
	add app_version VARCHAR(200);

alter table users
	add last_location jsonb not null;

alter table users
	add device_id varchar(200) not null;

alter table users
	add date_registration date not null;

alter table users
	add date_last_profile_update timestamp not null;

alter table users
	add date_last_login timestamp not null;

alter table users
	add date_deletion timestamp not null;

alter table users
	add deleted boolean not null;
