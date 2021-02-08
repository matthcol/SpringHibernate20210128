package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import movieapp.entity.Movie;

@DataJpaTest // active Spring Data avec sa couche JPA Hibernate
class TestMovieRepository {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private EntityManager entityManager;

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
		// 1 - a title of movies to read int the test  
		final String title = "The Man Who Knew Too Much";
		// 2 - writing data in database via the entity manager
		List<Movie> moviesDatabase = List.of(
				new Movie(title, 1934, null),
				new Movie(title, 1956, null),
				new Movie("The Man Who Knew Too Little", 1997, null));
		moviesDatabase.forEach(entityManager::persist); // SQL : insert for each movie
		entityManager.flush();
		// when : read from the repository
		var moviesFound = movieRepository.findByTitle(title);
		// then 
		assertEquals(2, moviesFound.size());
		assertAll(moviesFound.stream().map(
				m -> ()->assertEquals(title, m.getTitle(), "title")));
//		for (Movie m: moviesFound) {
//			assertEquals(title, m.getTitle(), "title");
//		}
	}
	
	@Test
	void testFindByTitleContainingIgnoreCase() {
		// giving
		// 1 - a title of movies to read int the test  
		final String titlePart = "mAn";
		// 2 - writing data in database via the entity manager
		List<Movie> moviesDatabase = List.of(
				new Movie("The Man Who Knew Too Much", 1934, null),
				new Movie("The Invisible Man", 2020, null),
				new Movie("Wonder Woman 1984", 2020, null),
				new Movie("Men In Black", 1997, null));
		moviesDatabase.forEach(entityManager::persist); // SQL : insert for each movie
		entityManager.flush();
		// when : read from the repository
		var moviesFound = movieRepository.findByTitleContainingIgnoreCase(titlePart);
		// then 
		assertEquals(3, moviesFound.size());
		assertAll(moviesFound.stream().map(
			m -> ()->assertTrue(
					m.getTitle().toLowerCase().contains(titlePart.toLowerCase()),
					titlePart + " not in title")));
//		for (Movie m: moviesFound) {
//			assertEquals(title, m.getTitle(), "title");
//		}
	}
	
	@Test
	void testFindByYearBetween() {
		// giving
		// 1 - a title of movies to read int the test  
		final int yearMin = 1977;
		final int yearMax = 1995;
		// 2 - writing data in database via the entity manager
		List<Movie> moviesDatabase = List.of(
				new Movie("Dr No", 1962, null),
				new Movie("Licence To Kill", 1989, null),
				new Movie("The Spy Who Loved Me", 1977, null),
				new Movie("GoldenEye", 1995, null),
				new Movie("Spectre", 2015, null));
		moviesDatabase.forEach(entityManager::persist); // SQL : insert for each movie
		entityManager.flush();
		// when : read from the repository
		var moviesFound = movieRepository.findByYearBetweenOrderByYear(yearMin, yearMax);
		// then 
		assertEquals(3, moviesFound.size());
		assertAll(moviesFound.stream().map(
			m -> ()->assertTrue(
					(m.getYear() >= yearMin) && (m.getYear() <= yearMax),
					 "year " + m.getYear() + " not in interval ["
					 + yearMin + "-" +yearMax +"]")));
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
