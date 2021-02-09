package movieapp.persistence;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import movieapp.dto.ArtistStatistics;
import movieapp.dto.IArtistStatistics;
import movieapp.dto.INameYearTitle;
import movieapp.dto.NameYearTitle;
import movieapp.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
	
	Set<Artist> findByNameIgnoreCase(String name);
	Stream<Artist> findByNameEndingWithIgnoreCase(String name);
	
	@Query("select a from Artist a where extract(year from a.birthdate) = :year")
	Stream<Artist> findByBirthdateYear(int year);
	
	Stream<Artist> findByBirthdate(LocalDate birthdate);
	
	@Query("select a.name as name, m.year as year, m.title as title from Movie m join m.actors a where a.name like :name order by m.year")
	Stream<INameYearTitle> filmographyActor(String name);
	
	@Query("select a.id as artistId, a.name as artistName, count(*) as count, min(year) as minYear, max(year) as maxYear from Movie m join m.director a group by a having count(*) >= :countThreshold order by count(*) desc")
    Stream<IArtistStatistics> statisticsByDirector(long countThreshold);
	
	@Query("select new movieapp.dto.ArtistStatistics(a.id as artistId, a.name as artistName, count(*) as count, min(year) as minYear, max(year) as maxYear) from Movie m join m.actors a group by a having count(*) >= :countThreshold order by count(*) desc")
    Stream<ArtistStatistics> statisticsByActor(long countThreshold);
}
