package movieapp.service;

import java.util.List;

import movieapp.dto.MovieSimple;

public interface IMovieService {
	MovieSimple add(MovieSimple movie);
	List<MovieSimple> getAll();
}
