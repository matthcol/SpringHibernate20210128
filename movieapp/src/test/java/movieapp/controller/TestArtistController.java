package movieapp.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.JsonPath;

import movieapp.dto.ArtistSimple;
import movieapp.dto.MovieSimple;
import movieapp.service.IArtistService;

@WebMvcTest(ArtistController.class)
class TestArtistController {
	private static final String BASE_URI = "/api/artists";
	
	@MockBean
	IArtistService artistService;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void testGetByIdPresent() throws Exception {
		// 1. given
		int id = 1;
		String name = "Will Smith";
		LocalDate birthdate = LocalDate.of(1968, 9, 25);
		ArtistSimple artistMock = new ArtistSimple(id, name, birthdate);
		given(artistService.getById(id))
			.willReturn(Optional.of(artistMock));
		// 2. when
		mockMvc.perform(MockMvcRequestBuilders
				.get(BASE_URI + "/" + id)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value(name))
			.andExpect(jsonPath("$.birthdate").value(birthdate.toString()));
	}
	
	@Test
	void testGetByIdMissing() throws Exception {
		// 1. given
		int id = 1;
		given(artistService.getById(id))
			.willReturn(Optional.empty());
		// 2. when
		mockMvc.perform(MockMvcRequestBuilders
				.get(BASE_URI + "/" + id)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").doesNotExist());
	}

}
