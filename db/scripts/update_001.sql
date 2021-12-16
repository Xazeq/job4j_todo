create table if not exists items
(
    id serial primary key,
    description text,
    created timestamp,
    isDone boolean
);