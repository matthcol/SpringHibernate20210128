set role movie;

-- do not mix aggregate functions and column
-- select count(*) as nb_movies, title from movies;
select 
	count(*) as nb_movies, 
	min(year) as min_year,
	max(year) as max_year,
	sum(duration) / 60 as total_duration
from movies;

select year, count(*) as nb_movies
from movies
where year >= 1980
group by year
having count(*) >= 20
-- order by year desc;
order by nb_movies desc;

-- nb de films (année min, année max) par réalisateur
select
	-- s.*,
	s.id, s.name,
	count(*) as nb_movies, min(year) as min_year, max(year) as max_year
from 
	movies m join stars s on m.id_director = s.id
group by s.id
order by nb_movies desc;

-- nb de films (année min, année max) par acteur
select 
	s.id, s.name, 
	count(*) as nb_movies, min(year) as min_year, max(year) as max_year
from 
	stars s join play p on p.id_actor = s.id
	join movies m on p.id_movie = m.id
-- where s.name like '%McQueen'
group by s.id
order by nb_movies desc;
--order by s.name;

select 
	s.id, s.name, m.year, m.title
from 
	stars s join play p on p.id_actor = s.id
	join movies m on p.id_movie = m.id
where s.name like 'John Wayne'
order by m.year;

select 
	count(*),
	min(m.year) as min_year,
	max(m.year) as max_year,
	coalesce(sum(m.duration), 0) as total_duration
from movies m
where year = 2021;






