alter table device alter column os_version drop not null;

alter table device alter column model drop not null;

alter table device alter column push_notification drop not null;

alter table device alter column push_token drop not null;

alter table device
    add advertising_id varchar(255);

alter table device
    add creation_date timestamp not null;

