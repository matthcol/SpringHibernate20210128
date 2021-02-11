package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.persistence.ArtistRepository;
import movieapp.persistence.MovieRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestMapppingRole {

	@Autowired
	MovieRepository movieRepository;
	
	@Test
	void testReadActorsWithRole() {
		String title = "Gran Torino";
		var res = movieRepository.findByTitle(title);
		res.forEach(
				m -> {
					System.out.println("Movie: " + m);
					m.getPlays().stream().forEach(
							p -> System.out.println(
									"\t- " + p.getActor()
									+ " : " +p.getRole()));
				});
	}

}
