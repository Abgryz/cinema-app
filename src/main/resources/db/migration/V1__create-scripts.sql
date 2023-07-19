create table if not exists client(
	email varchar primary key,
	full_name varchar,
	address varchar,
	birth_date date,
	active boolean default true,
	password varchar not null
);

create table if not exists job_tittle(
	job_tittle_name varchar primary key,
	salary int not null check(salary > 0)
);

create table if not exists employee(
	email varchar primary key,
	full_name varchar not null,
	emp_date date default now(),
	address varchar,
	birth_date date,
	active boolean default true,
	password varchar not null,
	job_tittle_name varchar not null references job_tittle(job_tittle_name)
);

create table if not exists film(
	film_id serial primary key,
	rental_date date not null check(rental_date < now()),
	duration time,
	director_full_name varchar,
	film_cast varchar,
	film_name varchar not null,
	description text,
	image varchar,
	emp_email varchar not null references employee(email)
);

create table if not exists genre(
	genre_name varchar primary key
);

create table if not exists film_genre(
	genre_name varchar references genre(genre_name),
	film_id int references film(film_id),
	primary key (genre_name, film_id)
);

create table if not exists hall(
	hall_id serial primary key,
	type varchar not null
);

create table if not exists seat(
	seat_id serial primary key,
	seat_num int not null check(seat_num > 0),
	seat_row int not null check(seat_row > 0),
	price_coef numeric default 1 check(price_coef > 0),
	hall_id int not null references hall(hall_id),
	unique(seat_num, seat_row, hall_id)
);

create table if not exists cinema_show(
	cinema_show_id serial primary key,
	date_and_time timestamp not null check(date_and_time > now()),
	hall_id int not null references hall(hall_id),
	film_id int not null references film(film_id),
	unique(hall_id, date_and_time)
);

create table if not exists ticket(
	ticket_id serial primary key,
	price numeric not null check(price > 0),
	cinema_show_id int not null references cinema_show(cinema_show_id),
	seat_id int not null references seat(seat_id),
	unique(cinema_show_id, seat_id)
);

create table if not exists ticket_sales(
	ticket_id int primary key references ticket(ticket_id),
	is_booking boolean default false,
	sale_date date default now(),
	cl_email varchar not null references client(email),
	emp_email varchar references employee(email)
);

