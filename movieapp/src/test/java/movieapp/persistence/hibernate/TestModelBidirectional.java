package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.persistence.ArtistRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestModelBidirectional {
	
	@Autowired
	ArtistRepository artistRepository;

	@Test
	void testReadMoviesDirected() {
		String name = "Clint Eastwood";
		var res = artistRepository.findByNameEndingWithIgnoreCase(name);
		res.forEach(
				a -> System.out.println(a.getDirectedMovies()));
	}

}
