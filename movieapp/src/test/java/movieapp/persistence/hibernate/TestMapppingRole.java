package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import movieapp.entity.Movie;
import movieapp.entity.Play;
import movieapp.persistence.ArtistRepository;
import movieapp.persistence.MovieRepository;
import movieapp.persistence.PlayRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestMapppingRole {

	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	PlayRepository playRepository;
	
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
	
	@Test
	void testCreateMovieWithActors() {
		// create and save movie
		Movie movie = new Movie("Not Time To Die", 2021, null);
		movieRepository.save(movie);
		// fetch actors
		var daniel = artistRepository.findById(185819).get();
		var ralph = artistRepository.findById(146).get();
		// create and save plays
		var plays = List.of(
				new Play(movie, daniel, "James Bond"),
				new Play(movie, ralph, "M"));
		movie.setPlays(plays);
		plays.forEach(playRepository::save);
		System.out.println(daniel);
		System.out.println(ralph);
		movieRepository.flush();
	}

}
