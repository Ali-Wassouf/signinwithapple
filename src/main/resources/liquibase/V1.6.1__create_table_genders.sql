create table genders
(
    id bigserial not null,
    name VARCHAR(100) not null
);

create unique index genders_id_uindex
    on genders (id);

create unique index genders_name_uindex
    on genders (name);

alter table genders
    add constraint genders_pk
        primary key (id);

