package movieapp.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import movieapp.dto.MovieSimple;
import movieapp.entity.Movie;
import movieapp.persistence.MovieRepository;
import movieapp.service.IMovieService;

@Service
@Transactional
public class MovieServiceJpa implements IMovieService {
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public MovieSimple add(MovieSimple movie) {
		Movie movieEntityIn = modelMapper.map(movie, Movie.class);
		Movie movieEntityOut = movieRepository.save(movieEntityIn);
		MovieSimple movieDtoRes = modelMapper.map(movieEntityOut, MovieSimple.class);
		return movieDtoRes;
	}

	@Override
	public List<MovieSimple> getAll() {
		// TODO Auto-generated method stub
		var movie = new MovieSimple();
		movie.setTitle("Blade Runner");
		return List.of(movie);
	}

}
