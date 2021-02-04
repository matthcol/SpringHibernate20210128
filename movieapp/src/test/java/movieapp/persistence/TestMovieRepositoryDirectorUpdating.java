package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import movieapp.entity.Artist;
import movieapp.entity.Movie;

@DataJpaTest
class TestMovieRepositoryDirectorUpdating {

	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	TestEntityManager entityManager;
	//EntityManager entityManager;
	
	@Test
	void testSaveMovieWithDirector() {
		Artist clint = new Artist("Clint Eastwood", LocalDate.of(1930,5,31));
		artistRepository.save(clint); // not necessary if persist in cascade
		Movie movie = new Movie("Unforgiven", 1992, 130);
		movie.setDirector(clint);
		movieRepository.save(movie); // eventually persist director if cascade set
		System.out.println("Write: " + movie + " with director: " + movie.getDirector());
		// read data from database
		entityManager.clear();
		// select movie0_.id as id1_1_0_, movie0_.id_director as id_direc5_1_0_, movie0_.duration as duration2_1_0_, movie0_.title as title3_1_0_, movie0_.year as year4_1_0_, artist1_.id as id1_0_1_, artist1_.birthdate as birthdat2_0_1_, artist1_.deathdate as deathdat3_0_1_, artist1_.name as name4_0_1_ 
		// from movie movie0_ left outer join artist artist1_ on movie0_.id_director=artist1_.id 
		// where movie0_.id=?
		Movie movieRead = entityManager.find(Movie.class, movie.getId());
		System.out.println("Read: " + movieRead + " with director: " + movieRead.getDirector());
		assertNotNull(movieRead.getDirector()); 
	}
	
	@Test
	void testSetDirectorWithExistingMovieAndArtist() {
		// write data in database
		Artist artist = new Artist("Clint Eastwood", LocalDate.of(1930,5,31));
		Movie movie = new Movie("Unforgiven", 1992, 130);
		entityManager.persist(artist);
		entityManager.persist(movie);
		entityManager.flush();
		int idArtist = artist.getId();
		int idMovie = movie.getId();
		// clear hibernate cache
		entityManager.clear();
		// read movie and artist from database
		var optArtistRead = artistRepository.findById(idArtist);
		var optMovieRead = movieRepository.findById(idMovie);
		assertTrue(optArtistRead.isPresent());
		assertTrue(optMovieRead.isPresent());
		var artistRead = optArtistRead.get();
		var movieRead = optMovieRead.get();
		System.out.println("Read: " + artistRead);
		System.out.println("Read: " + movieRead + " with director: " + movieRead.getDirector());
		// set association
		movieRead.setDirector(artistRead);
		// synchronize Jpa Repository
		// update movie set id_director=?, duration=?, title=?, year=? where id=?
		movieRepository.flush();
		// TODO : empty cache and read again data to check director association
	}
		

}
