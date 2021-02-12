package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import movieapp.entity.Language;
import movieapp.entity.Movie;

@DataJpaTest
class TestMoviePoo {

	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	void test() {
		var movie = new Movie("Bullet in the Head", 1990, 136);
		movie.setLanguage(Language.ZH);
		movie.setGenres(List.of("Action", "Crime", "Drama"));
		entityManager.persist(movie);
		entityManager.flush();
		var id = movie.getId();
		entityManager.clear();
		var movieDb = movieRepository.findById(id);
		movieDb.ifPresent(m->{
			System.out.println(m);
			System.out.println("Genres: " + m.getGenres());
			System.out.println("Language: " + m.getLanguage());
		});
	}

}
