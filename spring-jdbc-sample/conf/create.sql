
create sequence region_seq minvalue 0;
create table region (
    id int primary key not null default nextval('region_seq'),
    name varchar(25) not null unique,
    comment varchar(250) 
);

create sequence nation_seq minvalue 0;
create table nation (
    id int primary key not null default nextval('nation_seq'),
    name varchar(25) not null unique,
    population int,
    region_id int not null references region (id)
);


