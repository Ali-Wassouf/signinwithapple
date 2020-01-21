alter table users rename column username to auth_provider_user_id;

alter table users
    add auth_provider varchar(20);

alter table users rename column password to auth_provider_token;