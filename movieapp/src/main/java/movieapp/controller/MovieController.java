package movieapp.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import movieapp.entity.Movie;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	/**
	 * url /api/movies
	 * @return
	 */
	@GetMapping 
	public List<Movie> movies() {
		return List.of(
				new Movie("Blade Runner", 1982, 117), 
				new Movie("Kabir Singh", 2019, 173));
	}
	
	/**
	 * url /api/movies/un
	 * @return
	 */
	@GetMapping("/un")
	public Movie movie() {
		return new Movie("Kabir Singh", 2019, 173);
	}
	
	@PostMapping
	@ResponseBody
	public Movie addMovie(@RequestBody Movie movie) {
		// TODO : persist movie object
		System.out.println(movie);
		return movie;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
