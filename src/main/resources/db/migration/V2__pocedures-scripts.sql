-- FUNCTION: public.check_cinema_show_time()

-- DROP FUNCTION IF EXISTS public.check_cinema_show_time();

CREATE OR REPLACE FUNCTION public.check_cinema_show_time()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
begin
    if exists (

        select *
        from cinema_show
        	join ticket using(cinema_show_id)
		where date_and_time <= now()
			and new.ticket_id = ticket_id

    ) then
        raise exception 'Cannot sell a ticket for a session that has already passed!';
    end if;
    return new;
end;
$BODY$;

ALTER FUNCTION public.check_cinema_show_time()
    OWNER TO postgres;


-- FUNCTION: public.check_time_diff()

-- DROP FUNCTION IF EXISTS public.check_time_diff();

CREATE OR REPLACE FUNCTION public.check_time_diff()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
begin
    if exists (

        select *
        from cinema_show
        	join film using(film_id)
		where ((new.date_and_time - (date_and_time + duration)) < interval '15 minutes'
			and (new.date_and_time - (date_and_time)) > interval '-15 minutes')
    		and new.hall_id = hall_id

    ) then
        raise exception 'Incorrect cinema show start time';
    end if;
    return new;
end;
$BODY$;

ALTER FUNCTION public.check_time_diff()
    OWNER TO postgres;


-- FUNCTION: public.delete_cinema_shows()

-- DROP FUNCTION IF EXISTS public.delete_cinema_shows();

CREATE OR REPLACE FUNCTION public.delete_cinema_shows()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
begin
  delete from cinema_show
  where film_id = old.film_id;
  return old;
end;
$BODY$;

ALTER FUNCTION public.delete_cinema_shows()
    OWNER TO postgres;


-- FUNCTION: public.delete_film()

-- DROP FUNCTION IF EXISTS public.delete_film();

CREATE OR REPLACE FUNCTION public.delete_film()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
begin
  delete from cinema_show
  where film_id = old.film_id;

  delete from film_genre
  where film_id = old.film_id;

  return old;
end;
$BODY$;

ALTER FUNCTION public.delete_film()
    OWNER TO postgres;


-- FUNCTION: public.delete_tickets()

-- DROP FUNCTION IF EXISTS public.delete_tickets();

CREATE OR REPLACE FUNCTION public.delete_tickets()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
begin
  delete from ticket
  where cinema_show_id = old.cinema_show_id;
  return old;
end;
$BODY$;

ALTER FUNCTION public.delete_tickets()
    OWNER TO postgres;


-- FUNCTION: public.search_films(name)

-- DROP FUNCTION IF EXISTS public.search_films(name);

CREATE OR REPLACE FUNCTION public.search_films(
	text name)
    RETURNS TABLE(film_id integer, rental_date date, duration time without time zone, director_full_name character varying, film_cast character varying, film_name character varying, description text, image character varying, emp_email character varying)
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
begin
	return query
	select film.*
	from film
	where lower(film.film_name) like '%' || lower($1) || '%'
		and film.image is not null;
end;
$BODY$;

ALTER FUNCTION public.search_films(name)
    OWNER TO postgres;
