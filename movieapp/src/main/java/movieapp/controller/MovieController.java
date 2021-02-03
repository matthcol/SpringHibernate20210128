package movieapp.controller;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import movieapp.entity.Movie;
import movieapp.persistence.MovieRepository;

@Transactional
@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	/**
	 * path /api/movies
	 * @return list of movies in the repository
	 */
	@GetMapping 
	@ResponseBody
	public List<Movie> movies() {
//		return List.of(
//				new Movie("Blade Runner", 1982, 117), 
//				new Movie("Kabir Singh", 2019, 173));
		return movieRepository.findAll();
	}
	
	/**
	 * path /api/movies/1
	 * @param id id of the movie to find in the repository
	 * @return movie with this id in the repository or Optional empty if not found
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<Movie> movie(@PathVariable("id") int id) {
		// return new Movie("Kabir Singh", 2019, 173);
		return movieRepository.findById(id);
	}
	
	/**
	 * path /api/movies/byTitle?t=Spectre
	 * @param title
	 * @return
	 */
	@GetMapping("/byTitle")
	public List<Movie> moviesByTitle(@RequestParam("t") String title) {
		return movieRepository.findByTitle(title);
	}
	
	/**
	 * path /api/movies/byTitleYear?t=Spectre&y=2015
	 * @param title
	 * @param year
	 * @return
	 */
	@GetMapping("/byTitleYear")
	public List<Movie> moviesByTitleYear(
			@RequestParam("t") String title, 
			// @RequestParam(value="y", defaultValue = "2020") int year)
			@RequestParam(value="y", required=false) Integer year)
	{
		if (Objects.isNull(year)) {
			return movieRepository.findByTitle(title);
		} else {
			return movieRepository.findByTitleAndYear(title, year);
		}
	}
	
	/**
	 * paths 
	 * 	/api/movies/byYearRange?mi=1950&ma=1980
	 *  /api/movies/byYearRange?mi=1950
	 *  /api/movies/byYearRange?ma=1980
	 */
	@GetMapping("/byYearRange")
	@ResponseBody
	public List<Movie> moviesByYearRange(
			@RequestParam(value="mi", required=false) Integer minYear,
			@RequestParam(value="ma", required=false) Integer maxYear)
	{
		if (Objects.nonNull(minYear)) {
			if (Objects.nonNull(maxYear)) {
				return movieRepository.findByYearBetweenOrderByYear(minYear, maxYear);
			} else {
				return movieRepository.findByYearGreaterThanEqual(minYear);
			}
		} else if (Objects.nonNull(maxYear)) {
			return movieRepository.findByYearLessThanEqual(maxYear);
		} else {
			return List.of();
		}
	}
	
	
	/**
	 * path /api/movies
	 * @param movie movie to add in the repository
	 * @return movie added in the repository and completed (id, default values)
	 */
	@PostMapping
	@ResponseBody
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepository.save(movie); // insert movie
	}

	/**
	 * path /api/movies
	 * @param movie
	 * @return
	 */
	@PutMapping
	public Optional<Movie> updateMovie(@RequestBody Movie movie) {
		// read movie from database/repository
		Optional<Movie> optMovieDb = movieRepository.findById(movie.getId());
		optMovieDb.ifPresent(m -> {
			m.setTitle(movie.getTitle());
			m.setYear(movie.getYear());
			m.setDuration(movie.getDuration());
			// movieRepository.flush(); // done by @Transactional
		});
		return optMovieDb;
	}
	
	/**
	 * path /api/movies
	 * @param movie movie to delete according to its id
	 * @return movie deleted from repository
	 */
	@DeleteMapping
	public Optional<Movie> deleteMovie(@RequestBody Movie movie) {
		return deleteMovieById(movie.getId());
	}
	
	/**
	 * url /api/movies/1
	 */
	@DeleteMapping("/{id}")
	public Optional<Movie> deleteMovieById(@PathVariable("id") int id) {
		Optional<Movie> optMovieDb = movieRepository.findById(id);
		optMovieDb.ifPresent(m -> movieRepository.deleteById(m.getId()));
		return optMovieDb;
	}
	
	
}
