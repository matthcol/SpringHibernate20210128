package movieapp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

// POJO: Plain Old Java Object
// Bean
@Entity // persistance via une table
@Table(name = "movies")
public class Movie {
	// implicit default constructor if none written

	private Integer id;
	private String title;
	private Integer year;
	private Integer duration;
	private Language language;
	private Set<String> genres;
	private Map<Language, String> titles;
	
	private Artist director;
	private List<Artist> actors;
	
	public Movie() {
		super();
		actors = new ArrayList<>();
		genres = new HashSet<>();
		titles = new HashMap<>();
	}

	public Movie(String title, Integer year, Integer duration) {
		this();
		this.title = title;
		this.year = year;
		this.duration = duration;
	}
	
	@Id // primary key (unique + not null)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, length = 300)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(nullable = false)
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Column(nullable = true)  // default for nullable
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Enumerated(EnumType.STRING)
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@ElementCollection (fetch = FetchType.EAGER)
	@CollectionTable(name="movies_genres", 
		joinColumns = @JoinColumn(name="id_movie", nullable = false))
	@Column(name="genre", nullable = false)
	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	// NB: (id_movie, lang) is the primary key  
	@ElementCollection (fetch = FetchType.EAGER)  
	@CollectionTable(name="movies_all_titles", 
		joinColumns = @JoinColumn(name="id_movie"))
	@MapKeyColumn(name = "lang")
	@MapKeyEnumerated(EnumType.STRING)
    @Column(name = "title")
	public Map<Language, String> getTitles() {
		return titles;
	}

	public void setTitles(Map<Language, String> titles) {
		this.titles = titles;
	}

	// optional director
	@ManyToOne 
	@JoinColumn(name="id_director", nullable=true)
	public Artist getDirector() {
		return director;
	}

	public void setDirector(Artist director) {
		this.director = director;
	}

	@ManyToMany
	@JoinTable(
			name="play",
			joinColumns = @JoinColumn(name="id_movie"), // FK to this entity (Movie)
			inverseJoinColumns = @JoinColumn(name="id_actor")) // FK to the other entity (Artist)
	public List<Artist> getActors() {
		return actors;
	}

	public void setActors(List<Artist> actors) {
		this.actors = actors;
	}

	// NB: never put associations in this method to keep fetch lazy/eager setting 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append(title)
			.append("(")
			.append(year)
			.append(", ")
			.append(duration)
			.append(" mn)#")
			.append(id)
			.toString();  // finalize String result
	}
}
