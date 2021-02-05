package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import movieapp.entity.Artist;
import movieapp.entity.Movie;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // do not replace DB of app with H2 
class TestHibernateQueriesJPQL {

	@Autowired
	//TestEntityManager entityManager;
	EntityManager entityManager;
	
	@Test
	void test_select_all_as_list() {
		// select movie0_.id as id1_0_, movie0_.id_director as id_direc5_0_, movie0_.duration as duration2_0_, movie0_.title as title3_0_, movie0_.year as year4_0_ 
		// from movies movie0_
		TypedQuery<Movie> query = entityManager.createQuery(
				// "from Movie",
				"select m from Movie m", // JPQL request
				Movie.class);	// result will be adapted in 0, 1 ou * object Movie
		List<Movie> movies = query.getResultList(); // execute + convert resultset as list
		System.out.println("Nb movies: " + movies.size());
	}
	
	@Test
	void test_select_all_as_stream() {
		entityManager.createQuery(
				"select m from Movie m", // JPQL request
				Movie.class)	// result will be adapted in 0, 1 ou * object Movie
			.getResultStream() // execute + convert resultset as stream
			.limit(10)
			.forEach(System.out::println);
	}

}
