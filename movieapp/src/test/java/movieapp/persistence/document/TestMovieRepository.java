package movieapp.persistence.document;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

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
		System.out.println(movie);
		// save in repo mongoDb
		movieRepository.save(movie);
	}

}
