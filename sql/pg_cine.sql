SET CLIENT_ENCODING = 'UTF-8';

DROP SCHEMA movie cascade;
DROP USER if exists movie;

create user movie LOGIN password 'password';

create schema movie authorization movie;

set role movie;

-- DDL : tables, views
\i pg_cine_ddl.sql
-- DATA: psql is in no transaction mode by default
\i cine_data_stars.sql;
\i cine_data_movies.sql;
\i cine_data_play.sql;
-- readjust sequences
select setval('stars_id_seq', max(id)) from stars;
select setval('movies_id_seq', max(id)) from movies;
