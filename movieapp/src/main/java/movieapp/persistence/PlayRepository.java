package movieapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import movieapp.entity.Play;

public interface PlayRepository extends JpaRepository<Play, Integer> {

}
