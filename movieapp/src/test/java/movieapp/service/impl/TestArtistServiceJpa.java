package movieapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.eq;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import movieapp.dto.ArtistSimple;
import movieapp.entity.Artist;
import movieapp.persistence.ArtistRepository;

@ExtendWith(MockitoExtension.class)
class TestArtistServiceJpa {

	// layer to mock
	@Mock
	ArtistRepository artistRepository;
	
	// layer to test using layer mocked
	@InjectMocks
	ArtistServiceJpa artistService;
	
	@Test
	void testGetById() {
		// 1. given
		int id = 1;
		String name = "Will Smith";
		LocalDate birthdate = LocalDate.of(1968, 9, 25);
		// perfect answer from mock
		Artist artistEntity = new Artist(name, birthdate);
		artistEntity.setId(id);
		given(artistRepository.findById(id))
			.willReturn(Optional.of(artistEntity));
		// 2. when
		Optional<ArtistSimple> optArtistSimpleDto = artistService.getById(id);
		// 3. then
		then(artistRepository)
			.should()
			.findById(eq(id));
		assertTrue(optArtistSimpleDto.isPresent());
		optArtistSimpleDto.ifPresent(
				artistSimpleDto -> assertAll(
						() -> assertEquals(id, artistSimpleDto.getId()),
						() -> assertEquals(name, artistSimpleDto.getName()),
						() -> assertEquals(birthdate, artistSimpleDto.getBirthdate())));
	}

}








