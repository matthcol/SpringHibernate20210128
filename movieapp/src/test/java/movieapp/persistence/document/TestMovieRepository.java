package movieapp.persistence.document;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import movieapp.persistence.mongodb.MovieRepository;

@DataMongoTest
class TestMovieRepository {

	@Autowired
	MovieRepository movieRepository;
	
	@Test
	void testCreate() {
		var director = Artist.of("Ridley Scott");
		var movie = Movie.builder()
				.title("Blade Runner")
				.year(1982)
				.director(director)
				.build();
		System.out.println("Before saving: " + movie);
		// save in repo mongoDb
		movieRepository.save(movie);
		System.out.println("After saving: " + movie);
	}
	
	@Test //@AfterEach
	void testDeleteAll() {
		movieRepository.deleteAll();
	}
	
	static IntStream numberMovie(){
		// return IntStream.of(1,2,3,4,5,6,7);
		return IntStream.rangeClosed(1, 100);
	}
	
	@ParameterizedTest
	@MethodSource("numberMovie")
	void testCreateFranchise(int number) {
		var movie = Movie.of("Star Wars " + number, 1977);
		// save in repo mongoDb
		movieRepository.save(movie);
		System.out.println("After saving: " + movie);
	}
	
	@Test
	void testFindAll() {
		var movies = movieRepository.findAll();
		System.out.println(movies);
	}
	
	@Test
	void testFindAllPagination() {
		var movies = movieRepository.findAll(PageRequest.of(0, 5))
				.toList();
		System.out.println(movies);
	}
}






