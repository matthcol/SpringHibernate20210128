package movieapp.service.impl.jpa;

// java
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// converter
import org.modelmapper.ModelMapper;

// spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service interface
import movieapp.service.IMovieService;
import movieapp.dto.IMovieStatistics;
import movieapp.dto.IMovieYearCount;
// Dto
import movieapp.dto.MovieDetail;
import movieapp.dto.MovieDetailDirectorActors;
import movieapp.dto.MovieSimple;
import movieapp.persistence.entity.Artist;
import movieapp.persistence.entity.Movie;
import movieapp.persistence.repository.ArtistRepository;
import movieapp.persistence.repository.MovieRepository;


@Service
@Transactional
public class MovieServiceJpa implements IMovieService {
	@Autowired
	MovieRepository movieRepository; 
	
	@Autowired
	ArtistRepository artistRepository; // for director and actors
	
	@Autowired
	ModelMapper modelMapper;

	// CREATE method
	
	@Override
	public MovieDetail add(MovieDetail movie) {
		// convert DTO to entity
		Movie movieEntityIn = modelMapper.map(movie, Movie.class);
		// persist entity
		Movie movieEntityOut = movieRepository.save(movieEntityIn);
		// convert entity back to DTO
		MovieDetail movieDtoRes = modelMapper.map(movieEntityOut, MovieDetail.class);
		// return DTO completed (id, default values, ...)
		return movieDtoRes;
	}
	
	// UPDATE methods
	
	@Override
	public Optional<MovieDetail> update(MovieDetail movie) {
		// find entity to update if exists
		// otherwise return empty optional
		return movieRepository.findById(movie.getId())
				.map(me-> {
					// update entity with DTO
					modelMapper.map(movie, me);
					// convert back entity to DTO
					return modelMapper.map(me, MovieDetail.class);
				});
	}
	
	@Override
	public Optional<MovieDetailDirectorActors> setDirector(
			int idMovie, int idDirector) 
	{
		return movieRepository.findById(idMovie) // fetch movie
			.flatMap(me -> artistRepository
				.findById(idDirector)	// fetch director if movie exists
				.map(ae -> {	
					// set director if exists
					me.setDirector(ae);
					// return movie Dto with director/actors
					return modelMapper.map(me, MovieDetailDirectorActors.class);
				}));
	}

	@Override
	public Optional<MovieDetailDirectorActors> setActors(int idMovie, List<Integer> idActors) {
		return movieRepository.findById(idMovie) // fetch movie
			.map(me -> {
				// fetch actors
				List<Artist> artistEntities = artistRepository.findAllById(idActors);
				if (artistEntities.size() !=  idActors.size()) {
					// cancel update
					return null;
				}
				me.setActors(artistEntities);
				return modelMapper.map(me, MovieDetailDirectorActors.class);
			});
	}

	// DELETE method
	
	@Override
	public Optional<MovieDetail> deleteMovieById(int id) {
		return movieRepository.findById(id)
			.map(me->{
				me.setDirector(null);
				me.setActors(List.of());
				movieRepository.delete(me);
				return modelMapper.map(me, MovieDetail.class);
			});
	}

	// READ methods
	
	@Override
	public List<MovieSimple> getAll() {
		// find entities
		List<Movie> moviesEntity = movieRepository.findAll();
		// convert entities to DTOs
		List<MovieSimple> moviesDto = moviesEntity.stream()
			.map(me -> modelMapper.map(me, MovieSimple.class))
			.collect(Collectors.toList());
		// return DTOs
		return moviesDto;
		// same thing without intermediate variables
//		return movieRepository.findAll()
//				.stream()
//				.map(me -> modelMapper.map(me, MovieSimple.class))
//				.collect(Collectors.toList());
	}

	@Override
	public Optional<MovieDetailDirectorActors> getById(int id) {
		// find entity if exists
		Optional<Movie> optMovieEntity =  movieRepository.findById(id);
		// convert entity to dto if exists
		// otherwise make empty optional result
		Optional<MovieDetailDirectorActors> optMovieDto = optMovieEntity.map(
				me -> modelMapper.map(me, MovieDetailDirectorActors.class));
		return optMovieDto;
		// same thing without intermediate variables
//		return movieRepository.findById(id)
//				.map(me -> modelMapper.map(me, MovieDetailDirectorActors.class));
	}


	@Override
	public List<MovieSimple> getByTitle(String title) {
		return movieRepository.findByTitleContainingIgnoreCase(title)
				.map(me->modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MovieSimple> getByTitleYear(String title, int year) {
		return movieRepository.findByTitleAndYearOrderByYear(title, year)
				.map(me->modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MovieSimple> getByYearRange(int minYear, int maxYear) {
		return movieRepository.findByYearBetweenOrderByYear(minYear, maxYear)
				.map(me->modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());	
	}

	@Override
	public List<MovieSimple> getByYearLess(int maxYear) {
		return movieRepository.findByYearLessThanEqual(maxYear)
				.map(me -> modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MovieSimple> getByYearGreater(int minYear) {
		return movieRepository.findByYearGreaterThanEqual(minYear)
				.map(me -> modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public IMovieStatistics getStatistics() {
		return movieRepository.statisticsDtoI();
	}

	@Override
	public List<IMovieYearCount> getCountMovieByYear(int yearMin, int countMin) {
		return movieRepository.countMovieByYear(yearMin, countMin)
				.collect(Collectors.toList());
	}

}
