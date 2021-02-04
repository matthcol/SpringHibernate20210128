package movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import movieapp.entity.Artist;
import movieapp.persistence.ArtistRepository;

@Transactional
@RestController
@RequestMapping("/api/artists")
public class ArtistController {
	
	@Autowired
	ArtistRepository artistRepository;
	
	@PostMapping
	@ResponseBody
	public Artist addArtist(@RequestBody Artist artist) {
		return artistRepository.save(artist);
	}

}
