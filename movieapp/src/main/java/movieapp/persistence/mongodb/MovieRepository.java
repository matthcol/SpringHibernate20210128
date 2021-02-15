package movieapp.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import movieapp.persistence.document.Movie;

public interface MovieRepository extends MongoRepository<Movie, Integer>{

}
