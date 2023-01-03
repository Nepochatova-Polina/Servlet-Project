create table if not exists ships
(
    id              serial primary key,
    name varchar(255),
    passenger_capacity integer
);
alter table ships
    owner to "user";
