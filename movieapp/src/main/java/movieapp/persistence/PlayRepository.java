package movieapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import movieapp.entity.Play;

public interface PlayRepository extends JpaRepository<Play, Integer> {
	List<Play> findByActorName(String name);
}
