package movieapp.persistence.document;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMovie {

	@Test
	void testCreateDefault() {
		var movie = new Movie();
		movie.setTitle("Blade Runner");
		movie.setYear(1982);
		System.out.println(movie);
	}
	
	@Test
	void testCreateAllArgs() {
		var movie = new Movie("1", "Blade Runner", 1982, 117, null, null);
		System.out.println(movie);
	}
	
	@Test
	void testCreateRequiredArgConstructor() {
		var movie = Movie.of("Blade Runner", 1982);
		System.out.println(movie);
	}
	
	@Test
	void testCreateBuilder() {
		var director = Artist.of("Ridley Scott");
		var movie = Movie.builder()
				.title("Blade Runner")
				.year(1982)
				.director(director)
				.build();
		System.out.println(movie);
	}

}
