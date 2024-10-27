create table authors
(
    id          bigint auto_increment primary key,
    birth_date  date null,
    birth_place varchar(255) null,
    created_at  datetime(6) default current_timestamp(6),
    death_date  date null,
    description varchar(255) null,
    image       longtext null,
    name        varchar(100) not null,
    nationality varchar(255) null,
    stage_name  varchar(255) null,
    updated_at  datetime(6) default current_timestamp(6) on update current_timestamp(6),
    website     varchar(255) null
);

create table books
(
    id           bigint auto_increment primary key,
    categories   varchar(255) null,
    created_at   datetime(6) default current_timestamp(6),
    description  varchar(255) null,
    genres       varchar(255) null,
    image        longtext null,
    language     varchar(50) null,
    published_at bigint null,
    publisher    varchar(100) null,
    title        varchar(255) not null,
    total_pages  int null,
    updated_at   datetime(6) default current_timestamp(6) on update current_timestamp(6)
);

create table author_books
(
    author_id bigint not null,
    book_id   bigint not null,
    primary key (author_id, book_id),
    constraint fk_author_books_author
        foreign key (author_id) references authors (id) on delete cascade,
    constraint fk_author_books_book
        foreign key (book_id) references books (id) on delete cascade
);

create table reviews
(
    id       bigint auto_increment primary key,
    content  text null,
    image    longtext null,
    reviewer varchar(255) null,
    time     bigint null,
    book_id  bigint not null,
    created_at   datetime(6) default current_timestamp(6),
    updated_at   datetime(6) default current_timestamp(6) on update current_timestamp(6),
    constraint fk_reviews_book
        foreign key (book_id) references books (id) on delete cascade
);
