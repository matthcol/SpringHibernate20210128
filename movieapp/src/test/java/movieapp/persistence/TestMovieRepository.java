package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static testing.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

import movieapp.persistence.entity.Movie;
import movieapp.persistence.provider.MovieProvider;
import movieapp.persistence.repository.MovieRepository;
import testing.persistence.DatabaseUtils;

@DataJpaTest // active Spring Data avec sa couche JPA Hibernate
//@AutoConfigureTestDatabase(replace = Replace.NONE) // deactivate H2 +
//@ActiveProfiles("test") // + DB from application-test.properties
class TestMovieRepository {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	@Test
	void testCount() {
		// TODO: write data in DB
		long nb_movies = movieRepository.count();
		System.out.println(nb_movies);
		// TODO: assert nb_movies is good
	}
	
	@Test
	void testFindByTitle() {
		// giving
		// 1 - a title of movies to read in the test  
		var title = "The Man Who Knew Too Much";
		var otherTitle = "The Man Who Knew Too Little";
		var goodTitles = List.of(title, title);
		var goodYears = List.of(1934, 1956);
		// 2 - writing data in database via the entity manager
		var moviesDatabase = MovieProvider.moviesGoodOnesOneBad(
				goodTitles, goodYears, otherTitle, 1997);
		DatabaseUtils.insertDataFlushAndClearCache(entityManager, moviesDatabase);
		// when : read from the repository
		var moviesFound = movieRepository.findByTitle(title)
				.collect(Collectors.toList());
		var movieTitlesFound = moviesFound.stream()
				.map(Movie::getTitle)
				.collect(Collectors.toList());
		var movieYearsFound = moviesFound.stream()
				.map(Movie::getYear)
				.collect(Collectors.toList());
		System.out.println(moviesFound);
		System.out.println(movieTitlesFound);
		System.out.println(movieYearsFound);
		// then 
		assertSizeEquals(goodTitles, movieTitlesFound, "number of titles");
		assertAllEquals(title, movieTitlesFound, "title");
		assertCollectionUniqueElementEquals(goodYears, movieYearsFound, "year");
	}
	
	@Test
	void testFindByTitleContainingIgnoreCase() {
		// giving
		// 1 - a title of movies to find in the database  
		var titlePart = "mAn";
		// 2 - writing data in database via the entity manager
		var titlesContaingWord = List.of(
				"The Man Who Knew Too Much",
				"The Invisible Man", 
				"Wonder Woman 1984");
		var moviesDatabase = MovieProvider.moviesGoodOnesOneBad(
				titlesContaingWord,
				List.of(1934, 2020, 2020),
				"Men In Black", 1997);
		DatabaseUtils.insertDataFlushAndClearCache(entityManager, moviesDatabase);
		// when : read from the repository
		var movieTitlesFound = movieRepository.findByTitleContainingIgnoreCase(titlePart)
				.map(Movie::getTitle)
				.collect(Collectors.toList());
		// then 
		var titlePartLowerCase = titlePart.toLowerCase();
		assertSizeEquals(titlesContaingWord, movieTitlesFound, "number of movies");
		assertAllTrue(
				movieTitlesFound, 
				t -> t.toLowerCase().contains(titlePartLowerCase),
				t -> titlePartLowerCase + " not in title " + t);
	}
	
	@Test
	void testFindByYearBetween() {
		// giving
		// 1 - a title of movies to read int the test  
		var yearMin = 1977;
		var yearMax = 1995;
		// 2 - writing data in database via the entity manager
		var goodTitles = List.of(
				"GoldenEye", "Licence To Kill", "The Spy Who Loved Me"); 
		var moviesDatabase = List.of(
				new Movie("Dr No", 1962, null), // nok
				new Movie("Licence To Kill", 1989, null), // ok
				new Movie("The Spy Who Loved Me", 1977, null), // ok
				new Movie("GoldenEye", 1995, null), // ok
				new Movie("Spectre", 2015, null)); // nok
		DatabaseUtils.insertDataFlushAndClearCache(entityManager, moviesDatabase);
		// when : read from the repository
		var moviesFound = movieRepository.findByYearBetweenOrderByYear(yearMin, yearMax)
				.collect(Collectors.toList());
		// then 
		assertSizeEquals(goodTitles, moviesFound, "number of movies");
		assertAllTrue(moviesFound,
			m -> (m.getYear() >= yearMin) && (m.getYear() <= yearMax),
			m -> "year " + m.getYear() + " not in interval ["
					 + yearMin + "-" +yearMax +"]");
	}
	
	@Test
	void testFindByYearOrderByTitle() {
		int year = 2020;
		List<Movie> moviesDatabase = List.of(
				new Movie("Dr No", 1962, null),
				new Movie("The Invisible Man", 2020, null),
				new Movie("Wonder Woman 1984", 2020, null),
				new Movie("Tyler Rake", 2020, null),
				new Movie("Tenet", 2020, null),
				new Movie("Outside the Wire", 2021, null));
		moviesDatabase.forEach(entityManager::persist); // SQL : insert for each movie
		entityManager.flush();
		// when
		var movies = movieRepository.findByYearOrderByTitle(year);
		System.out.println(movies);
		// TODO: assert
	}
	
	@Test
	void testFindByYearBetweenSort() {
		List<Movie> moviesDatabase = List.of(
				new Movie("Dr No", 1962, 110),
				new Movie("Licence To Kill", 1989, 133),
				new Movie("The Spy Who Loved Me", 1977, 125),
				new Movie("GoldenEye", 1995, 130),				
				new Movie("Spectre", 2015, 148),
				new Movie("Octopussy", 1983, 131),
				new Movie("Never Say Never Again", 1983, 130));
		moviesDatabase.forEach(entityManager::persist); 
		entityManager.flush();
		// when
		// order by movie0_.year asc
		var moviesByYear = movieRepository.findByYearBetween(1960, 2020, Sort.by("year"));
		System.out.println(moviesByYear);
		// order by movie0_.duration desc
		var moviesByDurationDesc = movieRepository.findByYearBetween(1960, 2020, 
				Sort.by(Direction.DESC, "duration"));
		System.out.println(moviesByDurationDesc);
		// order by movie0_.duration asc, movie0_.title asc
		var moviesByDurationTitle = movieRepository.findByYearBetween(1960, 2020, 
				Sort.by("year", "title"));
		System.out.println(moviesByDurationTitle);
	}
	
	
	
	@ParameterizedTest
	@ValueSource(strings = {
			"Z", 
			"Blade Runner", 
			"Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil Mutant Hellbound Flesh Eating Crawling Alien Zombified Subhumanoid Living Dead, Part 5"})
	void testSaveTitle(String title) {
		// given
		int year = 1982;
		int duration = 173;
		// when + then
		saveAssertMovie(title, year, duration);
	}
			
	@Test
	void testSaveTitleEmptyNOK() {
		String title = null;
		int year = 1982;
		int duration = 173;
		assertThrows(DataIntegrityViolationException.class, 
				() -> saveAssertMovie(title, year, duration));
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 1888, 1982, Integer.MAX_VALUE })
	void testSaveYear(int year) {
		// given
		String title = "Blade Runner";
		int duration = 173;
		// when + then
		saveAssertMovie(title, year, duration);
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 1, 120, Integer.MAX_VALUE })
	@NullSource
	void testSaveDuration(Integer duration) {
		// given
		String title = "Blade Runner";
		int year = 1982;
		// when + then
		saveAssertMovie(title, year, duration);
	}
	
	
	@Test
	void testSaveYearNullNOK() {
		// given
		String title = "Blade Runner";
		Integer year = null;
		int duration = 173;
		// when + then
		assertThrows(DataIntegrityViolationException.class,
				() -> saveAssertMovie(title, year, duration));
	}

	private void saveAssertMovie(String title, Integer year, Integer duration) {
		Movie movie = new Movie(title, year, duration);
		// when
		movieRepository.save(movie);
		// then
		var idMovie = movie.getId();
		assertNotNull(idMovie, "id generated by database");
		// NB : following test only checks that object read is the same as object written (cache)
		movieRepository.findById(idMovie)
			.ifPresent(m -> assertEquals(movie, m));
	}

}
