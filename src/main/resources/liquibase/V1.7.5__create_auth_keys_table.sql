create table auth_keys
(
    -- Only integer types can be auto increment
    id bigserial UNIQUE not null,
    refresh_token varchar(255) not null,
    is_valid boolean default true not null
);


alter table users
    add auth_keys_id int not null;

create unique index users_auth_keys_id_uindex
    on users (auth_keys_id);

alter table users
    add constraint users__auth_keys_fk
        foreign key (auth_keys_id) references auth_keys (id);

