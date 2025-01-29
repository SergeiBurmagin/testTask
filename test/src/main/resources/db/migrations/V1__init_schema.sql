DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS email_data CASCADE;
DROP TABLE IF EXISTS phone_data CASCADE;
DROP TABLE IF EXISTS shared_locks CASCADE;


create table if not exists user(
    id bigserial,
    name varchar(500),
    date_of_birth date,
    password varchar(500),

    constraint pk_users primary key(id)
);


create table if not exists accounts(
    id bigserial,
    user_id bigint,
    balance decimal not null default 0 check (balance > 0),
    initial_balance decimal not null default 0 check (balance > 0),

    constraint pk_accounts primary key(id),
    constraint fk_accounts_on_user_id foreign key (user_id) references user(id)
);

create unique index unidx_accounts_on_user_id on accounts(user_id);


create table if not exists email_data(
    user_id bigint,
    email varchar(200),

    constraint pk_email_data primary key (user_id, email),
    constraint fk_email_data_on_user_id foreign key (user_id) references user(id)
);


create table if not exists phone_data(
    user_id bigint,
    phone varchar(13),

    constraint pk_phone_data primary key (user_id, phone),
    constraint fk_phone_data_on_user_id foreign key (user_id) references user(id)
);


create table if not exists shared_locks
(
    key     varchar(100),
    value   varchar(50)  not null,
    created timestamptz not null,
    updated timestamptz not null,

    constraint pk_shared_locks primary key(key)
);


create index idx_shared_locks_on_created on shared_locks(created);