package movieapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import movieapp.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{

}
