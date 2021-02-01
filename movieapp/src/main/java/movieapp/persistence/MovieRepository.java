package movieapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import movieapp.entity.Movie;

// paramètres de généricité :
//	T = Movie : objets gérés par le répository
//	ID= Integer : type de la clé primaire
// By Default use database in Memory H2
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	// gifts : save/findAll/findById/...
	
	List<Movie> findByTitle(String title);
}
