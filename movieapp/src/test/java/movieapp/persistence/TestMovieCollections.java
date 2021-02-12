package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.entity.ImageColor;
import movieapp.entity.Movie;

@DataJpaTest // Base sur la H2
class TestMovieCollections {

	@Autowired
	MovieRepository movieRepository;
	
	@Test
	void test() {
		Movie movieK = new Movie("Kingsman: The Secret Service", 2014, 129);
		movieK.setGenres(List.of("Action", "Adventure", "Comedy"));
		movieK.setImageColor(ImageColor.COLOR);
		movieRepository.save(movieK);
		Movie movieX = new Movie("xXx", 2002, 124);
		movieX.setGenres(List.of("Action", "Adventure", "Thriller"));
		movieK.setImageColor(ImageColor.BLACK_AND_WHITE);
		movieRepository.save(movieX);
		movieRepository.flush();
	}

}
