create or replace function check_cinema_show_time()
    returns trigger as $$
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
$$ language plpgsql;
create or replace trigger check_ticket_sales
    before insert or update on ticket_sales
    for each row
execute function check_cinema_show_time();

create or replace function check_time_diff()
    returns trigger as $$
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
$$ language plpgsql;
create or replace trigger check_cinema_show_time_diff
    before insert on cinema_show
    for each row
execute function check_time_diff();

create or replace function delete_tickets()
    returns trigger as
$$
begin
    delete from ticket
    where cinema_show_id = old.cinema_show_id;
    return old;
end;
$$
    language plpgsql;
create or replace trigger delete_tickets_trigger
    before delete on cinema_show
    for each row
execute function delete_tickets();

create or replace function delete_film()
    returns trigger as
$$
begin
    delete from cinema_show
    where film_id = old.film_id;
    delete from film_genre
    where film_id = old.film_id;
    return old;
end;
$$
    language plpgsql;
create or replace trigger delete_film_trigger
    before delete on film
    for each row
execute function delete_film();
