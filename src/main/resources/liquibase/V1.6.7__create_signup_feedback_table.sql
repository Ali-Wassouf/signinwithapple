create table signup_feedback
(
    user_id bigserial not null,
    rating int,
    reason varchar(255) not null,
    comment text,
    date_created timestamp default now() not null
);

comment on column signup_feedback.date_created is 'NOW()';

create unique index signup_feedback_user_id_uindex
    on signup_feedback (user_id);

alter table signup_feedback
    add constraint signup_feedback_pk
        primary key (user_id);

