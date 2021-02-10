package movieapp.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import movieapp.dto.ArtistSimple;
import movieapp.service.IArtistService;




@RestController
@RequestMapping("/api/artists")
public class ArtistController {
	
	@Autowired
	IArtistService artistService;
	
	@GetMapping("/{id}")
	@ResponseBody
	Optional<ArtistSimple> getById(@PathVariable("id") int id){
		return artistService.getById(id);
	}
	

}
