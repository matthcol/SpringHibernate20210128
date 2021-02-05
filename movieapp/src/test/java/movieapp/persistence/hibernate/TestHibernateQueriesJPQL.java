package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
	
	@ParameterizedTest
	@ValueSource(ints = {2019, 2020, 2021})
	void test_select_where_year(int year) {
		System.out.println(" - Movies from year: " + year);
		// select movie0_.id as id1_0_, movie0_.id_director as id_direc5_0_, movie0_.duration as duration2_0_, movie0_.title as title3_0_, movie0_.year as year4_0_ 
		// from movies movie0_ where movie0_.year= :year
		entityManager.createQuery(
				"select m from Movie m where m.year = :year",
				Movie.class)
			.setParameter("year", year)
			.getResultStream()
			.forEach(System.out::println);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1934,The Man Who Knew Too Much",
		"1956,The Man Who Knew Too Much"
	})
	void test_select_where_title_year(int year, String title) {
		System.out.println(" - Movies with title,year: ");
		entityManager.createQuery(
				"select m from Movie m where m.year = :year and m.title = :title",
				Movie.class)
			.setParameter("year", year)
			.setParameter("title", title)
			.getResultStream()
			.forEach(System.out::println);
	}
	
	
	@Test
	void test_select_where_year_birthdate() {
		entityManager.createQuery(
				"select a from Artist a where extract(year from a.birthdate) = :year",
				Artist.class)
			.setParameter("year", 1930)
			.getResultStream()
			.limit(10)
			.forEach(System.out::println);
	}
	
	// artists of age ? this year
	@Test
	void test_select_where_age() {
		entityManager.createQuery(
				"select a from Artist a where extract(year from current_date) - extract(year from a.birthdate) = :age",
				Artist.class)
			.setParameter("age", 30)
			.getResultStream()
			.limit(10)
			.forEach(System.out::println);
	}
	
	@Test
	void test_select_movie_with_director_named() {
		// select movie0_.id as id1_0_, movie0_.id_director as id_direc5_0_, movie0_.duration as duration2_0_, movie0_.title as title3_0_, movie0_.year as year4_0_ 
		// from movies movie0_ inner join stars artist1_ on movie0_.id_director=artist1_.id 
		// where artist1_.name=?
		entityManager.createQuery(
				"select m from Movie m join m.director a where a.name = :name",
				Movie.class)
			.setParameter("name", "Clint Eastwood")
			.getResultStream()
			.forEach(System.out::println);
		
	}
	
	@Test
	void test_select_movie_with_actor_named() {
		// select movie0_.id as id1_0_, movie0_.id_director as id_direc5_0_, movie0_.duration as duration2_0_, movie0_.title as title3_0_, movie0_.year as year4_0_ 
		// from movies movie0_ inner join stars artist1_ on movie0_.id_director=artist1_.id 
		// where artist1_.name=?
		entityManager.createQuery(
				"select m from Movie m join m.actors a where a.name = :name",
				Movie.class)
			.setParameter("name", "Clint Eastwood")
			.getResultStream()
			.forEach(System.out::println);
		
	}
	
	

}
