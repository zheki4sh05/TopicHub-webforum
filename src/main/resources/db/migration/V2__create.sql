create sequence if not exists article_id_seq
    as integer
    start with 1;

create sequence if not exists "articlePart_article_seq"
    as integer;

create sequence if not exists hub_id_seq
    as integer
    start with 1;

create sequence if not exists article_hub_seq
    as integer
    start with 1;

create sequence if not exists likes_article_id_seq
    as integer;

create table if not exists hub
(
    id serial
        primary key,
    ru varchar not null
        constraint hub_ru
            unique,
    en varchar not null
        constraint hub_en
            unique
);

create table author
(
    id       uuid    not null
        constraint user_pkey
            primary key,
    login    varchar not null
        constraint login_uniq
            unique,
    email    varchar not null
        constraint email_uniq
            unique,
    password varchar not null,
    status   varchar,
    logo_id  varchar
);



create table article
(
    id         bigint  default nextval('article_id_seq'::regclass) not null
        primary key,
    theme      varchar                                             not null,
    keywords   varchar,
    created    timestamp with time zone,
    hub        integer default nextval('article_hub_seq'::regclass)
        constraint hub_fk
            references hub
            on update set null on delete set null,
    author_id  uuid
        constraint author_fk
            references author
            on update cascade on delete cascade,
    status     varchar,
    preview_id varchar
);

create index article_created
    on article (created);

alter sequence article_id_seq owned by article.id;

alter sequence article_hub_seq owned by article.hub;

create table articlepart
(
    val     varchar                                                       not null,
    article bigint default nextval('"articlePart_article_seq"'::regclass) not null
        constraint articlepart_article_id_fk
            references article
            on update cascade on delete cascade,
    type    varchar                                                       not null,
    name    varchar,
    id      integer,
    uuid    uuid                                                          not null
        primary key,
    created bigint
);





alter sequence "articlePart_article_seq" owned by articlepart.article;


create table user_role
(
    id     uuid not null
        primary key,
    userid uuid not null
        constraint user_fk
            references author
            on update cascade on delete cascade,
    role   varchar
);





create table likes
(
    id         uuid                                                     not null
        primary key,
    user_id    uuid
        constraint fkf6cmb748mfw6r86r78ckgser1
            references author
            on update set null on delete set null,
    article_id bigint default nextval('likes_article_id_seq'::regclass) not null,
    state      integer                                                  not null
);




create table subscription
(
    id       uuid not null
        constraint subscription_pk
            primary key,
    author   uuid
        constraint subscription_author_id_fk
            references author
            on update cascade on delete cascade,
    follower uuid
        constraint subscription_author_id_fk2
            references author
            on update cascade on delete cascade,
    constraint unique_field1_field2
        unique (author, follower)
);





create table comment
(
    id             uuid      not null
        constraint comment_pk
            primary key,
    message        varchar   not null,
    parent_comment uuid
        constraint parent___fk
            references comment
            on update cascade on delete cascade,
    article        bigint
        constraint article_fk
            references article
            on update cascade on delete cascade,
    author         uuid
        constraint comment_author_id_fk
            references author
            on update cascade on delete cascade,
    created        timestamp not null
);





create table bookmark
(
    id      uuid   not null
        constraint bookmarks_pk
            primary key,
    article bigint not null
        constraint bookmarks_article_id_fk
            references article
            on update cascade on delete cascade,
    author  uuid   not null
        constraint bookmarks_author_id_fk
            references author
            on update cascade on delete cascade,
    constraint bookmark_pk
        unique (author, article)
);




create table complaint_article
(
    id      uuid   not null
        constraint complaint_article_pk
            primary key,
    title   varchar,
    body    varchar,
    author  uuid
        constraint author___fk
            references author
            on update cascade on delete cascade,
    article bigint not null
        constraint article___fk
            references article
            on update cascade on delete cascade,
    date    timestamp with time zone
);





create table complaint_comment
(
    id      uuid not null
        constraint complaint_comment_pk
            primary key,
    title   varchar,
    body    varchar,
    author  uuid
        constraint author___fk
            references author
            on update cascade on delete cascade,
    comment uuid not null
        constraint comment___fk
            references comment,
    date    timestamp with time zone
);



create table token
(
    access_token  varchar not null,
    refresh_token varchar not null,
    is_logged_out boolean not null,
    user_id       uuid,
    id            uuid    not null
        constraint token_pk
            primary key
);







create or replace view article_info
            (id, theme, keywords, status, created, hub, author_id, likes, dislikes, comments_count, preview_id) as
SELECT a.id,
       a.theme,
       a.keywords,
       a.status,
       a.created,
       a.hub,
       a.author_id,
       COALESCE(sum(
                        CASE
                            WHEN r.state = 1 THEN 1
                            ELSE 0
                            END), 0::bigint) AS likes,
       COALESCE(sum(
                        CASE
                            WHEN r.state = '-1'::integer THEN 1
                        ELSE 0
                        END), 0::bigint) AS dislikes,
       COALESCE(count(c.id), 0::bigint)      AS comments_count,
       a.preview_id
FROM article a
         LEFT JOIN likes r ON a.id = r.article_id
         LEFT JOIN comment c ON a.id = c.article
GROUP BY a.id, a.theme, a.keywords, a.status, a.created, a.hub, a.author_id, a.preview_id;



insert into author (id, login, email, password,status, logo_id)
values ('a904e8b8-9da8-4535-b402-9be0b78b2981', 'admin','admin@mail.ru','$2a$10$ge8NA/v8CxTh1QoicotF3usUM61yGkIj73eS7xLab.IMrp8aNvmui', 'ACTIVE',null);
insert into user_role (id, userid, role)
values ('5a34fa56-e294-4602-8e7f-24e7a7832c2c', 'a904e8b8-9da8-4535-b402-9be0b78b2981','USER');
insert into user_role (id, userid, role) values ('c5086606-b949-4426-a340-2626f19f5ef2', 'a904e8b8-9da8-4535-b402-9be0b78b2981','ADMIN');
insert into hub (en, ru)
values ('Programming','Программирование');
insert into hub (en, ru)
values ('Science','Наука');
insert into hub (en, ru)
values ('Sport','Спорт');