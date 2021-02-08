package movieapp.persistence;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import movieapp.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
	
	Set<Artist> findByNameIgnoreCase(String name);
	Stream<Artist> findByNameEndingWithIgnoreCase(String name);
	
	@Query("select a from Artist a where extract(year from a.birthdate) = :year")
	Stream<Artist> findByBirthdateYear(int year);
	
	Stream<Artist> findByBirthdate(LocalDate birthdate);

}
