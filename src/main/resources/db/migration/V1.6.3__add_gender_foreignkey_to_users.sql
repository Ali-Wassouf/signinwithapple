alter table users drop column gender;

alter table users drop column gender_of_interest;

alter table users
    add gender bigint not null;

alter table users
    add gender_of_interest bigint not null;

alter table users
    add constraint users_genders_id_fk
        foreign key (gender) references genders;

alter table users
    add constraint users_genders_id_fk_2
        foreign key (gender_of_interest) references genders;

