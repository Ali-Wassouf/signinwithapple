
create table locations
(
    -- Only integer types can be auto increment
    id BIGSERIAL not null,
    latitude float not null,
    longitude float not null
);

create unique index locations_id_uindex
    on locations (id);

alter table locations
    add constraint locations_pk
        primary key (id);


alter table users alter column last_location type bigint using last_location::bigint;

alter table users
    add constraint users_locations_id_fk
        foreign key (last_location) references locations (id);