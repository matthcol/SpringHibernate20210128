package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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

	static Stream<Arguments> rangeYearSource() {
		return Stream.of(
				Arguments.arguments(2010, 2019),
				Arguments.arguments(2030, 2039));
	}
	
}
