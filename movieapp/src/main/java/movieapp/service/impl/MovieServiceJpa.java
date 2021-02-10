package movieapp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		List<Movie> moviesEntity = movieRepository.findAll();
		List<MovieSimple> moviesDto = moviesEntity.stream()
			.map(me -> modelMapper.map(me, MovieSimple.class))
			.collect(Collectors.toList());
		return moviesDto;
	}

	@Override
	public Optional<MovieSimple> getById(int id) {
		// TODO Auto-generated method stub
		Optional<Movie> optMovieEntity =  movieRepository.findById(id);
		Optional<MovieSimple> optMovieDto = optMovieEntity.map(
				me -> modelMapper.map(me, MovieSimple.class));
		return optMovieDto;
	}

	@Override
	public Optional<MovieSimple> update(MovieSimple movie) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<MovieSimple> setDirector(int idMovie, int idDirector) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<MovieSimple> deleteMovie(MovieSimple movie) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<MovieSimple> deleteMovieById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<MovieSimple> getByTitle(String title) {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public List<MovieSimple> getByTitleYear(String title, Integer year) {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public List<MovieSimple> getByYearRange(Integer minYear, Integer maxYear) {
		// TODO Auto-generated method stub
		return List.of();
	}
	
}
