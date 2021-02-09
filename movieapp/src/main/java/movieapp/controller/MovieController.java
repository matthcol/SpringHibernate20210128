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

import movieapp.dto.MovieSimple;
import movieapp.service.IMovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private IMovieService movieService;
	
//	@Autowired
//	private ArtistRepository artistRepository;
//	
	/**
	 * path /api/movies
	 * @return list of movies in the repository
	 */
	@GetMapping 
	@ResponseBody
	public List<MovieSimple> movies() {
//		return List.of(
//				new Movie("Blade Runner", 1982, 117), 
//				new Movie("Kabir Singh", 2019, 173));
		return movieService.getAll();
	}
	
	/**
	 * path /api/movies/1
	 * @param id id of the movie to find in the repository
	 * @return movie with this id in the repository or Optional empty if not found
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<MovieSimple> movie(@PathVariable("id") int id) {
		// return new Movie("Kabir Singh", 2019, 173);
		return movieService.getById(id);
	}
//	
//	/**
//	 * path /api/movies/byTitle?t=Spectre
//	 * @param title
//	 * @return
//	 */
//	@GetMapping("/byTitle")
//	public List<Movie> moviesByTitle(@RequestParam("t") String title) {
//		return movieRepository.findByTitle(title);
//	}
//	
//	/**
//	 * path /api/movies/byTitleYear?t=Spectre&y=2015
//	 * @param title
//	 * @param year
//	 * @return
//	 */
//	@GetMapping("/byTitleYear")
//	public List<Movie> moviesByTitleYear(
//			@RequestParam("t") String title, 
//			// @RequestParam(value="y", defaultValue = "2020") int year)
//			@RequestParam(value="y", required=false) Integer year)
//	{
//		if (Objects.isNull(year)) {
//			return movieRepository.findByTitle(title);
//		} else {
//			return movieRepository.findByTitleAndYear(title, year);
//		}
//	}
//	
//	/**
//	 * paths 
//	 * 	/api/movies/byYearRange?mi=1950&ma=1980
//	 *  /api/movies/byYearRange?mi=1950
//	 *  /api/movies/byYearRange?ma=1980
//	 */
//	@GetMapping("/byYearRange")
//	@ResponseBody
//	public List<Movie> moviesByYearRange(
//			@RequestParam(value="mi", required=false) Integer minYear,
//			@RequestParam(value="ma", required=false) Integer maxYear)
//	{
//		if (Objects.nonNull(minYear)) {
//			if (Objects.nonNull(maxYear)) {
//				return movieRepository.findByYearBetweenOrderByYear(minYear, maxYear);
//			} else {
//				return movieRepository.findByYearGreaterThanEqual(minYear);
//			}
//		} else if (Objects.nonNull(maxYear)) {
//			return movieRepository.findByYearLessThanEqual(maxYear);
//		} else {
//			return List.of();
//		}
//	}
	
	
	/**
	 * path /api/movies
	 * @param movie movie to add in the repository
	 * @return movie added in the repository and completed (id, default values)
	 */
	@PostMapping
	@ResponseBody
	public MovieSimple addMovie(@RequestBody MovieSimple movie) {
		return movieService.add(movie); // insert movie
	}

//	/**
//	 * path /api/movies
//	 * @param movie
//	 * @return
//	 */
//	@PutMapping
//	public Optional<Movie> updateMovie(@RequestBody Movie movie) {
//		// read movie from database/repository
//		Optional<Movie> optMovieDb = movieRepository.findById(movie.getId());
//		optMovieDb.ifPresent(m -> {
//			m.setTitle(movie.getTitle());
//			m.setYear(movie.getYear());
//			m.setDuration(movie.getDuration());
//			// movieRepository.flush(); // done by @Transactional
//		});
//		return optMovieDb;
//	}
//	
//	/**
//	 * path /api/movies/director?mid=1&did=3
//	 * @param idMovie
//	 * @param idDirector
//	 * @return
//	 */
//	@PutMapping("/director")
//	public Optional<Movie> setDirector(
//			@RequestParam("mid") int idMovie, 
//			@RequestParam("did") int idDirector) 
//	{
//		// chercher Movie et Artist correspondant à idMovie et idDirector
//		// si ok x 2
//		// alors movie.setDirector(artist)
//		// return optional avec le movie complété ou otional empty if missing entity
//		
//		Optional<Movie> optMovie = movieRepository.findById(idMovie);
//		Optional<Artist> optArtist = artistRepository.findById(idDirector);
//		if (optMovie.isEmpty() || optArtist.isEmpty()) {
//			return Optional.empty();
//		}
//		Movie movie = optMovie.get();
//		Artist artist = optArtist.get();
//		movie.setDirector(artist);
//		return Optional.of(movie);
//	}
//	
//	/**
//	 * NB: same thing in functional programming
//	 * path /api/movies/director2?mid=1&did=3
//	 * @param idMovie
//	 * @param idDirector
//	 * @return
//	 */
//	@PutMapping("/director2")
//	public Optional<Movie> setDirector2(
//			@RequestParam("mid") int idMovie, 
//			@RequestParam("did") int idDirector) 
//	{
//		return movieRepository.findById(idMovie)
//			.flatMap(m -> artistRepository.findById(idDirector)
//					.map(a -> {
//							m.setDirector(a);
//							return m;
//						}));
//		// SQL :
//		// - movie : select movie0_.id as id1_1_0_, movie0_.id_director as id_direc5_1_0_, movie0_.duration as duration2_1_0_, movie0_.title as title3_1_0_, movie0_.year as year4_1_0_, artist1_.id as id1_0_1_, artist1_.birthdate as birthdat2_0_1_, artist1_.deathdate as deathdat3_0_1_, artist1_.name as name4_0_1_ 
//		//		from movie movie0_ left outer join artist artist1_ on movie0_.id_director=artist1_.id 
//		//		where movie0_.id=?
//		// - artist : select artist0_.id as id1_0_0_, artist0_.birthdate as birthdat2_0_0_, artist0_.deathdate as deathdat3_0_0_, artist0_.name as name4_0_0_ 
//		//		from artist artist0_ where artist0_.id=?
//		// - director : update movie set id_director=?, duration=?, title=?, year=? where id=?
//	}
//	
//	/**
//	 * path /api/movies
//	 * @param movie movie to delete according to its id
//	 * @return movie deleted from repository
//	 */
//	@DeleteMapping
//	public Optional<Movie> deleteMovie(@RequestBody Movie movie) {
//		return deleteMovieById(movie.getId());
//	}
//	
//	/**
//	 * url /api/movies/1
//	 */
//	@DeleteMapping("/{id}")
//	public Optional<Movie> deleteMovieById(@PathVariable("id") int id) {
//		Optional<Movie> optMovieDb = movieRepository.findById(id);
//		optMovieDb.ifPresent(m -> movieRepository.deleteById(m.getId()));
//		return optMovieDb;
//	}
	
	
}
