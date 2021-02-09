package movieapp.dto;

import movieapp.entity.Artist;

public class ArtistStatistics {
	private Artist artist;
	private Long count;
	private Integer minYear;
	private Integer maxYear;
	
	public ArtistStatistics(Integer artistId, String artistName, Long count, Integer minYear, Integer maxYear) {
		super();
		this.artist = new Artist();
		this.artist.setId(artistId);
		this.artist.setName(artistName);
		this.count = count;
		this.minYear = minYear;
		this.maxYear = maxYear;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getMinYear() {
		return minYear;
	}

	public void setMinYear(Integer minYear) {
		this.minYear = minYear;
	}

	public Integer getMaxYear() {
		return maxYear;
	}

	public void setMaxYear(Integer maxYear) {
		this.maxYear = maxYear;
	}
		
}
