package modelmapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import movieapp.dto.MovieSimple;
import movieapp.entity.Movie;

class TestModelMapper {
	static ModelMapper modelMapper;
	
	@BeforeAll
	static void initModelMapper() {
		modelMapper = new ModelMapper();
	}

	@Test
	void testEntityToDto() {
		// entity
		Movie movieEntity = new Movie("Blade Runner", 1982, 117);
		movieEntity.setId(1);
		// convert to dto
		MovieSimple movieDto = modelMapper.map(movieEntity, MovieSimple.class);
		// is it ok
		System.out.println(movieDto.getTitle());
		assertEquals(movieEntity.getId(), movieDto.getId());
		assertEquals(movieEntity.getTitle(), movieDto.getTitle());
		assertEquals(movieEntity.getYear(), movieDto.getYear());
	}
	
	@Test
	void testDtoToEntity() {
		// DTO
		MovieSimple movieDto = new MovieSimple();
		movieDto.setTitle("Blade Runner");
		movieDto.setYear(1982);
		// convert to entity
		Movie movieEntity = modelMapper.map(movieDto, Movie.class);
		// is it ok
		System.out.println(movieEntity);
		assertEquals(movieDto.getId(), movieEntity.getId());
		assertEquals(movieDto.getTitle(), movieEntity.getTitle());
		assertEquals(movieDto.getYear(), movieEntity.getYear());
		assertNull(movieEntity.getDirector());
		assertTrue(movieEntity.getActors().isEmpty());
	}
	
	@Test
	void testDtoIntoEntity() {
		// DTO
		MovieSimple movieDto = new MovieSimple();
		movieDto.setTitle("Blade Runner (Director's cut)");
		movieDto.setYear(1982);
		movieDto.setId(1);
		// Entity
		Movie movieEntity = new Movie("Blade Runner", 1980, 117);
		movieEntity.setId(1);
		// update entity with dto
		modelMapper.map(movieDto, movieEntity);
		// is it ok
		System.out.println(movieEntity);
		assertEquals(movieDto.getId(), movieEntity.getId());
		assertEquals(movieDto.getTitle(), movieEntity.getTitle());
		assertEquals(movieDto.getYear(), movieEntity.getYear());
		assertEquals(117, movieEntity.getDuration()); // property not in dto
	}
	
	@Test
	void testEntitiesToDtos() {
		// entities
		Stream<Movie> entitySource = Stream.of(
				new Movie("Blade Runner", 1982, 117),
				new Movie("The Man Who Knew Too Much", 1934, null),
				new Movie("The Invisible Man", 2020, null),
				new Movie("Wonder Woman 1984", 2020, null));
		// convert to Dtos
		//List<MovieSimple> 
		var res = entitySource.map(me -> modelMapper.map(me, MovieSimple.class))
			.collect(Collectors.toCollection(
					// ArrayList::new
					// HashSet::new
					() -> new TreeSet<>(Comparator.comparing(MovieSimple::getTitle))
					));
		System.out.println(res);
		
	}

}
