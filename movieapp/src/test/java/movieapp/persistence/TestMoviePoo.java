package movieapp.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import movieapp.entity.Language;
import movieapp.entity.Movie;

@DataJpaTest
class TestMoviePoo {

	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	void testCreateMovieWithLanguageGenresTitles() {
		// given
		var title_en = "Bullet in the Head";
		var title_fr = "Une Balle dans la Tête";
		var title_zh = "喋血街頭"; // tradionnal chinese 
		var movie = new Movie(title_en, 1990, 136);
		var originalLanguage = Language.ZH;
		movie.setLanguage(originalLanguage);
		var genres = Set.of("Action", "Crime", "Drama");
		movie.setGenres(genres);
		var titles = Map.of(
				Language.EN, title_en,
				Language.FR, title_fr,
				Language.ZH,title_zh);
		movie.setTitles(titles);
		// when
		entityManager.persist(movie);
		entityManager.flush();
		var id = movie.getId();
		// then
		entityManager.clear();
		var movieDb = movieRepository.findById(id);
		assertTrue(movieDb.isPresent());
		movieDb.ifPresent(m->{
			System.out.println(m);
			System.out.println("Titles: " + m.getTitles());
			System.out.println("Genres: " + m.getGenres());
			System.out.println("Language: " + m.getLanguage());
			assertEquals(originalLanguage, m.getLanguage());
			assertIterableEquals(sortedSet(genres), sortedSet(m.getGenres()));
			assertIterableEquals(sortedSet(titles), sortedSet(m.getTitles()));
		});
	}

	// methods to compare iterables in the same order
	// TODO: put them in a test class toolbox
	private <T extends Comparable<? super T>> 
	SortedSet<T> sortedSet(Collection<? extends T> coll){
		return coll.stream().collect(Collectors.toCollection(TreeSet::new));
	}
	
	private <K extends Comparable<? super K>, V> 
	SortedSet<Entry<K, V>> sortedSet(Map<K, V> map){
		return map.entrySet().stream().collect(Collectors.toCollection(
				() -> new TreeSet<Entry<K,V>>(Comparator.comparing(Entry::getKey))));
	}
}
