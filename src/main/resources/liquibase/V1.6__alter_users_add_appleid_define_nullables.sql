alter table users alter column app_version set not null;

alter table users alter column date_registration type timestamp using date_registration::timestamp;

alter table users
    add apple_user_id VARCHAR(255) not null;

