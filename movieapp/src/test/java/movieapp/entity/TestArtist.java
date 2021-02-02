package movieapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestArtist {

	// TODO : unit tests of the java bean
	
	@Test
	void test() {
		var artists = List.of(
				new Artist("Steve McQueen", LocalDate.of(1930, 3, 24), LocalDate.of(1980, 11, 7)),
				new Artist("Steve McQueen", LocalDate.of(1969, 10, 9)),
				new Artist("Alfred Hitchcock"),
				new Artist());
		System.out.println(artists);
		
	}

}
