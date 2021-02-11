package movieapp.service;

import java.util.List;
import java.util.Optional;

import movieapp.dto.ArtistSimple;

public interface IArtistService {
	Optional<ArtistSimple> getById(int id);
	ArtistSimple add(ArtistSimple artist);
	List<ArtistSimple> getByName(String name);
}
