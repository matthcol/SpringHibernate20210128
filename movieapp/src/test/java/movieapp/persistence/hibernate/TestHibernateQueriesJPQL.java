package movieapp.persistence.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
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

import movieapp.dto.MovieStat;
import movieapp.dto.NameYearTitle;
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
		// from movies movie0_ 
		//		inner join play actors1_ on movie0_.id=actors1_.id_movie 
		//		inner join stars artist2_ on actors1_.id_actor=artist2_.id 
		// where artist2_.name=?
		entityManager.createQuery(
				"select m from Movie m join m.actors a where a.name = :name",
				Movie.class)
			.setParameter("name", "Clint Eastwood")
			.getResultStream()
			.forEach(System.out::println);
		
	}
	
	@Test
	void test_movie_one_stat() {
		// count(*)
		TypedQuery<Long> query = entityManager.createQuery(
			//	"select count(m) from Movie m",
				"select count(*) from Movie m",
				Long.class);
		long nb_movies = query.getSingleResult();
		System.out.println("Nb movies: " + nb_movies);
		// min(year)
		int min_year = entityManager.createQuery(
				"select min(m.year) from Movie m",
				Integer.class)
			.getSingleResult();
		System.out.println("year of first movie: " + min_year);
		// sum(duration) between year1 and year2
		long total_duration = entityManager.createQuery(
				"select coalesce(sum(m.duration),0) from Movie m where m.year between :year1 and :year2",
				Long.class)
			.setParameter("year1", 2021)
			.setParameter("year2", 2029)
			.getSingleResult();
		System.out.println("Total duration: " + total_duration);
		// min(duration) between year1 and year2
		Optional<Integer> min_duration = Optional.ofNullable(
			entityManager.createQuery(
				"select min(m.duration) from Movie m where m.year between :year1 and :year2",
				Integer.class) // type erasure
			.setParameter("year1", 2021)
			.setParameter("year2", 2029)
			.getSingleResult());
		System.out.println("Min duration: " + min_duration);
	}
	
	@Test
	void test_movie_several_stats_as_object_array() {
		var res = entityManager.createQuery(
				"select count(*), min(m.year), max(m.year) from Movie m",
				Object[].class)
			.getSingleResult();
		System.out.println("Movie stats: " + Arrays.toString(res)); //  + " (" + res.getClass() +")");
		long nb_movies = (long) res[0];
		int min_year = (int) res[1]; 
		int max_year = (int) res[2];
		System.out.println("Nb: " + nb_movies + " ; min: " + min_year + " ; max: " + max_year);		
	}
	
	@Test
	void test_movie_several_stats_as_tuple() {
		var res = entityManager.createQuery(
				"select count(*), min(m.year), max(m.year) from Movie m",
				Tuple.class)
			.getSingleResult();
		System.out.println("Movie stats: " + res); //  + " (" + res.getClass() +")");
		long nb_movies = res.get(0, Long.class);
		int min_year = res.get(1, Integer.class); 
		int max_year = res.get(2, Integer.class);
		System.out.println("Nb: " + nb_movies + " ; min: " + min_year + " ; max: " + max_year);		
	}
	
	@Test
	void test_movie_several_stats_as_dto() {
		var res = entityManager.createQuery(
				"select new movieapp.dto.MovieStat(count(*), min(m.year), max(m.year)) from Movie m",
				MovieStat.class)
			.getSingleResult();
		System.out.println("Movie stats: " + res); //  + " (" + res.getClass() +")");
		long nb_movies = res.getCount();
		int minYear = res.getMinYear(); 
		int maxYear = res.getMaxYear();
		System.out.println("Nb: " + nb_movies + " ; min: " + minYear + " ; max: " + maxYear);		
	}
	
	@Test
	void test_movie_projection() {
		String name = "John Wayne";
		//List<NameYearTitle> 
		var res = entityManager.createQuery(
				"select new movieapp.dto.NameYearTitle(a.name, m.year, m.title) from Movie m join m.actors a where a.name like :name order by m.year",
				NameYearTitle.class)
			.setParameter("name", name)
			.getResultStream()
			.limit(10)
			.collect(Collectors.toList());
		res.forEach(nyt -> System.out.println(nyt.getName() 
					+ " ; " + nyt.getYear() 
					+ " ; " + nyt.getTitle()));
				
	}
	
	// nb movies by year (params: thresholdCount, thresholdYear) order by year/count desc
	@Test
	void test_movie_stat_by_year() {
		entityManager.createQuery(
				"select m.year, count(*) from Movie m where m.year >= :yearT group by m.year having count(*) >= :countT order by m.year",
				Object[].class)
			.setParameter("yearT", 1980)
			.setParameter("countT", 20L)
			.getResultStream()
			.limit(10)
			.forEach(row -> System.out.println(Arrays.toString(row)));
	}
	
	// stats by director (count, min(year), max(year)) order by count desc
	@Test
	void test_movie_stat_by_director() {
		entityManager.createQuery(
				"select a, count(*), min(year), max(year) from Movie m join m.director a group by a order by count(*) desc",
				Object[].class)
		.getResultStream()
		.limit(10)
		.forEach(row -> System.out.println(Arrays.toString(row)));
	}
	
	
	// stats by actor (count, min(year), max(year)) order by count desc
	// TODO: query and test it
	
	@Test
	void test_movies_recent() {
		int deltaYear = 2;
		var res = entityManager.createQuery(
				"select m from Movie m where EXTRACT(YEAR FROM CURRENT_DATE) - m.year <= :deltaYear",
				Movie.class)
			.setParameter("deltaYear", deltaYear)
			.getResultList();
		System.out.println(res);
	}
	
	
	
	
}
