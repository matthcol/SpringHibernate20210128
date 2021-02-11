DROP TABLE if exists play;
DROP TABLE if exists movies;
DROP TABLE if exists stars;

create table stars (
	id integer auto_increment,
	name varchar(150) not null,
	birthdate date null,
	deathdate date null,
	constraint pk_star primary key(id)
);

create table movies (
	id integer auto_increment,
	title varchar(250) not null,
	year smallint not null,
	duration smallint null,
	genres varchar(200) null,
	synopsis text null,
	poster_uri varchar(300),
	id_director int null,
	CONSTRAINT pk_movie PRIMARY KEY(id),
	CONSTRAINT fk_movie_director FOREIGN KEY (id_director) 
			REFERENCES stars(id)
);

create table play(
	id_movie integer not null,
	id_actor integer not null,
	role varchar(100),
	constraint pk_play primary key(id_movie, id_actor),
	constraint fk_play_actor foreign key (id_actor) 
			references stars(id),
	constraint fk_play_movie foreign key (id_movie) 
			references movies(id)
);
