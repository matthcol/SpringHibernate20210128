package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.entity.Movie;
import movieapp.persistence.ArtistRepository;
import movieapp.persistence.MovieRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestSpringQueries {

	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Test
	void test_artists_by_birthdate_year() {
		int year = 1930;
		artistRepository.findByBirthdateYear(year)
			.limit(10)
			.forEach(System.out::println);
	}

	static Stream<Arguments> rangeYearSource() {
		return Stream.of(
				Arguments.arguments(2010, 2019),
				Arguments.arguments(2030, 2039));
	}
	
	@ParameterizedTest
//	@CsvSource({
//		"2010,2019",
//		"2030,2039"})
	@MethodSource("rangeYearSource")
	void test_total_duration_range_year(int yearMin, int yearMax) {
		var res = movieRepository.totalDuration(yearMin, yearMax);
		System.out.println(res);			
	}
	
	@ParameterizedTest
//	@CsvSource({
//		"2010,2019",
//		"2030,2039"})
	@MethodSource("rangeYearSource")
	void test_average_duration_range_year(int yearMin, int yearMax) {
		var res = movieRepository.averageDuration(yearMin, yearMax);
		System.out.println(res);
	}

	@Test
	void test_statistics() {
		var stats = movieRepository.statistics();
		long nb_movies = stats.getCount();
		int minYear = stats.getMinYear(); 
		int maxYear = stats.getMaxYear();
		System.out.println("Nb: " + nb_movies + " ; min: " + minYear + " ; max: " + maxYear);
	}
	
	@ParameterizedTest
	@ValueSource(strings= {
			"Clint Eastwood",
			"Tarantino"})
	void test_filmography(String name) {
		artistRepository.filmographyActor(name)
			.forEach(nyt -> System.out.println(nyt.getName() 
					+ " ; " + nyt.getYear() 
					+ " ; " + nyt.getTitle()
					+ " ; class: " + nyt.getClass()));
	}
	
	@Test
	void test_director_statistics() {
		long threshold = 30;
		artistRepository.statisticsByDirector(threshold)
			.forEach(as -> System.out.println(
					"director: " + as.getArtistId()
					+ "#" + as.getArtistName()
					+ " ; count: " + as.getCount()
					+ " ; years: [" + as.getMinYear() 
					+ "-"  + as.getMaxYear() + "]"));
			
	}
	
	@Test
	void test_actor_statistics() {
		long threshold = 30;
		artistRepository.statisticsByActor(threshold)
			.forEach(as -> System.out.println(
					"director: " + as.getArtist()
					+ " ; count: " + as.getCount()
					+ " ; years: [" + as.getMinYear() 
					+ "-"  + as.getMaxYear() + "]"));
			
	}

	
}
