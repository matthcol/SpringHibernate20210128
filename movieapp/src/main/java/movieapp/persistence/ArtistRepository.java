package movieapp.persistence;

import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import movieapp.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
	
	Set<Artist> findByNameIgnoreCase(String name);
	Stream<Artist> findByNameEndingWithIgnoreCase(String name);

}
