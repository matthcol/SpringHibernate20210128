SET client_encoding = 'UTF8'; 
set role movie;
--
-- Name: play2; Type: TABLE; Schema: movie; Owner: movie
--

CREATE TABLE play2 (
    id serial,
    id_movie int not null,
    id_actor int not null,
    role varchar(100),
	constraint pk_play2 primary key(id)
);
alter table play2 add constraint FK_PLAY2_MOVIE FOREIGN KEY (id_movie)
references movies(id);
alter table play2 add constraint FK_PLAY2_ACTOR FOREIGN KEY (id_actor)
references stars(id);

ALTER TABLE play2 OWNER TO movie;

-- DATA
\i cine_data_play_id.sql;
