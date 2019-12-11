alter table apple_users_auth alter column state set not null;

alter table apple_users_auth
    add refresh_token varchar(255) not null;

