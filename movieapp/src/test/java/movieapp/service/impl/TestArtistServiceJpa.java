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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import movieapp.dto.ArtistSimple;
import movieapp.entity.Artist;
import movieapp.persistence.ArtistRepository;
import movieapp.service.IArtistService;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TestArtistServiceJpa {

	// layer to mock
	// @Mock : pure mockito
	@MockBean // mock with spring IOC
	ArtistRepository artistRepository;
	
	// layer to test using layer mocked
	// @InjectMocks : pure mockito
	@Autowired
	IArtistService artistService;
	
	@Test
	void testGetByIdPresent() {
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
		// check mock has been called
		then(artistRepository)
			.should()
			.findById(eq(id));
		// check answer
		assertTrue(optArtistSimpleDto.isPresent());
		optArtistSimpleDto.ifPresent(
				artistSimpleDto -> assertAll(
						() -> assertEquals(id, artistSimpleDto.getId()),
						() -> assertEquals(name, artistSimpleDto.getName()),
						() -> assertEquals(birthdate, artistSimpleDto.getBirthdate())));
	}

	@Test
	void testGetByIdAbsent() {
		// 1. given : id with no corresponding data in repository
		int id = 0;
		// perfect answer from mock
		given(artistRepository.findById(id))
			.willReturn(Optional.empty());
		// 2. when
		Optional<ArtistSimple> optArtistSimpleDto = artistService.getById(id);
		// 3. then
		// check mock has been called
		then(artistRepository)
			.should()
			.findById(eq(id));
		// check answer
		assertTrue(optArtistSimpleDto.isEmpty());
	}
}








