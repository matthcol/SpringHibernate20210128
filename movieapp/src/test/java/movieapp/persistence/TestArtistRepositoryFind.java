package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.entity.Artist;

@DataJpaTest
public class TestArtistRepositoryFind {

	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	EntityManager entityManager;
	
	List<Artist> artists;
	List<Integer> ids;
	
	@BeforeEach
	void initData() {
		// write data in database (via hibernate entity manager)
		artists = List.of(
				new Artist("Steve McQueen", LocalDate.of(1930, 3, 24), LocalDate.of(1980, 11, 7)),
				new Artist("Steve McQueen", LocalDate.of(1969, 10, 9)),
				new Artist("Alfred Hitchcock"),
				new Artist("Steven R. McQueen"));
		artists.forEach(entityManager::persist);
		entityManager.flush();
		ids = artists.stream()
				// .map(a -> a.getId())
				.map(Artist::getId)
				.collect(Collectors.toList());
	}
	
	
	@Test
	void testFindAll() {
		// read data from database (via spring jpa repository)
		var artistsFound1 = artistRepository.findAll();
		System.out.println(artists);
		System.out.println(ids);
		System.out.println(artistsFound1);
		// TODO assert
	}
	
	@Test
	void testFindById() {
		var id = ids.get(0);
		var artistFound = artistRepository.findById(id);
		System.out.println(artists);
		System.out.println(ids);
		System.out.println(artistFound);
		assertTrue(artistFound.isPresent(), "artist found");
		artistFound.ifPresent(
				a -> assertEquals(id, a.getId(), "id artist"));
	}
	
	@Test
	void testFindAllById() {
		var idSelection = List.of(ids.get(0), ids.get(2));
		var artistsFound = artistRepository.findAllById(idSelection);
		System.out.println("Artists in DB: " + artists);
		System.out.println("Ids of artists in DB: " + ids);
		System.out.println("Ids asked: " + idSelection);
		System.out.println("Artists found: " + artistsFound);
		// TODO: assert
	}
	
	@Test
	void testGetOne() {
		var id = ids.get(0);
		System.out.println(artists);
		System.out.println(ids);
		var artistsFound3 = artistRepository.getOne(id);
		System.out.println(artistsFound3);
		// TODO assert
	}
	
	@Test
	void testFindByNameIgnoreCase() {
		String name = "Steve McQueen";
		var artists = artistRepository.findByNameIgnoreCase(name);
		System.out.println(artists);
//		for (var a: artists) {
//			assertEquals(name, a.getName());
//		}
		assertAll(artists.stream()
			.map(a -> () -> assertEquals(name, a.getName())));
	}
	
	@Test
	void testFindByNameEndingWithIgnoreCase() {
		String name = "mcqueen";
		var artists = artistRepository.findByNameEndingWithIgnoreCase(name);
		assertAll(artists
				// .map(a -> {System.out.println(a); return a;})
				.map(a -> () -> assertTrue(
						a.getName().toLowerCase().endsWith(name))));
	}
}




