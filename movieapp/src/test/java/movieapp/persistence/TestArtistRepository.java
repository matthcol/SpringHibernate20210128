package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.entity.Artist;

@DataJpaTest
public class TestArtistRepository {

	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	void testFind() {
		// write data in database (via hibernate entity manager)
		var artists = List.of(
				new Artist("Steve McQueen", LocalDate.of(1930, 3, 24), LocalDate.of(1980, 11, 7)),
				new Artist("Steve McQueen", LocalDate.of(1969, 10, 9)),
				new Artist("Alfred Hitchcock"));
		artists.forEach(entityManager::persist);
		entityManager.flush();
		// read data from database (via spring jpa repository)
		var artistsFound1 = artistRepository.findAll();
		System.out.println(artistsFound1);
		var artistsFound2 = artistRepository.findById(1);
		System.out.println(artistsFound2);
		var artistsFound3 = artistRepository.getOne(1);
		System.out.println(artistsFound3);
	}
}




