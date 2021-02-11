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
	void testReadMoviesDirectedAndPlayed() {
		String name = "Clint Eastwood";
		// select artist0_.id as id1_2_, artist0_.birthdate as birthdat2_2_, artist0_.deathdate as deathdat3_2_, artist0_.name as name4_2_ 
		// from stars artist0_ where upper(artist0_.name) like upper(?) escape ?
		var res = artistRepository.findByNameEndingWithIgnoreCase(name);
		res.forEach(
				a -> {
					System.out.println("Artist: " + a);
					// select directedmo0_.id_director as id_direc5_0_0_, directedmo0_.id as id1_0_0_, directedmo0_.id as id1_0_1_, directedmo0_.id_director as id_direc5_0_1_, directedmo0_.duration as duration2_0_1_, directedmo0_.title as title3_0_1_, directedmo0_.year as year4_0_1_ 
					// from movies directedmo0_ where directedmo0_.id_director=?
					System.out.println("Directed: " + a.getDirectedMovies());
					// select playedmovi0_.id_actor as id_actor2_1_0_, playedmovi0_.id_movie as id_movie1_1_0_, movie1_.id as id1_0_1_, movie1_.id_director as id_direc5_0_1_, movie1_.duration as duration2_0_1_, movie1_.title as title3_0_1_, movie1_.year as year4_0_1_, artist2_.id as id1_2_2_, artist2_.birthdate as birthdat2_2_2_, artist2_.deathdate as deathdat3_2_2_, artist2_.name as name4_2_2_ 
					// from play playedmovi0_ 
					//		inner join movies movie1_ on playedmovi0_.id_movie=movie1_.id 
					//		left outer join stars artist2_ on movie1_.id_director=artist2_.id 
					// where playedmovi0_.id_actor=?
					System.out.println("Played: " + a.getPlayedMovies());
				});
	}

}
