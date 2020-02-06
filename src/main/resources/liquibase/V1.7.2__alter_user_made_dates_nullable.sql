alter table users alter column date_last_profile_update drop not null;

alter table users alter column date_last_login drop not null;

alter table users alter column date_deletion drop not null;
