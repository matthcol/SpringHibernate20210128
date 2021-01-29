package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.entity.Movie;

@DataJpaTest
class TestMovieRepository {

	@Autowired
	private MovieRepository movieRepository;
	
	@Test
	void testSave() {
		// given
		String title = "Blade Runner";
		int year = 1982;
		int duration = 173;
		Movie movie = new Movie(title, year, duration);
		// when
		movieRepository.save(movie);
		// then
		assertNotNull(movie.getId());
		// debug purpose only
		System.out.println(movie);
	}

}
