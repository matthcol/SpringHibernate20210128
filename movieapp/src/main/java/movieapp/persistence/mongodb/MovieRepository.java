package movieapp.persistence.mongodb;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import movieapp.persistence.document.Movie;

public interface MovieRepository extends MongoRepository<Movie, Integer>{
	
	Stream<Movie> findByTitle(String title);
	Stream<Movie> findByDirectorName(String name);
	Page<Movie> findByYearGreaterThanOrderByYear(int year, Pageable pageable);

}
