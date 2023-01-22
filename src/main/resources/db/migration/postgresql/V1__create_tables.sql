create table categories
(
    id            bigserial not null,
    name          varchar   not null,
    slug          varchar   not null,
    description   varchar   not null,
    image         varchar,
    display_order numeric   not null,
    created_at    timestamp,
    updated_at    timestamp,
    primary key (id),
    CONSTRAINT category_name_unique UNIQUE (name),
    CONSTRAINT category_slug_unique UNIQUE (slug)
);

create table posts
(
    id         bigserial not null,
    title      varchar   not null,
    url        varchar,
    content    text      not null,
    cat_id     bigint    not null REFERENCES categories (id),
    votes      json,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);
