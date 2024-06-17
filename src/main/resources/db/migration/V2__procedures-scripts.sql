create or replace function search_films (text name)
    returns table ("film_id" int, "rental_date" date, "duration" time, "director_full_name" varchar, "film_cast" varchar, "film_name" varchar, "description" text, "image" varchar, "emp_email" varchar)
as $$
begin
    return query
        select film.*
        from film
        where lower(film.film_name) like '%' || lower($1) || '%'
          and film.image is not null;
end;
$$ language plpgsql;

create or replace procedure create_tickets_for_cinema_show(p_cinema_show_id bigint, p_price double precision)
    language plpgsql
as $$
declare
    seat int;
begin
    for seat in
        select seat_id from seat
        where hall_id = (select hall_id from cinema_show where cinema_show_id = p_cinema_show_id)
        loop
            insert into ticket (seat_id, cinema_show_id, price)
            values (seat, p_cinema_show_id, p_price);
        end loop;
end; $$;

create or replace procedure insert_seats(p_hall_id int, p_num_rows int, p_num_seats int)
    language plpgsql
as $$
begin
    for i in 1..p_num_rows loop
            for j in 1..p_num_seats loop
                    insert into seat (hall_id, seat_row, seat_num) values (p_hall_id, i, j);
                end loop;
        end loop;
end;
$$;
