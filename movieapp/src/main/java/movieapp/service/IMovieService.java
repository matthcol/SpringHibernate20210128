package movieapp.service;

import java.util.List;
import java.util.Optional;

import movieapp.dto.MovieSimple;

public interface IMovieService {
	// CREATE
	MovieSimple add(MovieSimple movie);
	// UPDATE
	Optional<MovieSimple> update(MovieSimple movie);
	Optional<MovieSimple> setDirector(int idMovie, int idDirector);
	// DELETE
	Optional<MovieSimple> deleteMovie(MovieSimple movie);
	Optional<MovieSimple> deleteMovieById(int id);
	// READ
	Optional<MovieSimple> getById(int id);
	List<MovieSimple> getAll();
	List<MovieSimple> getByTitle(String title);
	List<MovieSimple> getByTitleYear(String title, Integer year);
	List<MovieSimple> getByYearRange(Integer minYear, Integer maxYear);
}
