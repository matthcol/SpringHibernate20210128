package movieapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "stars")
public class Artist {
	private Integer id;
	private String name; // required 

	// Date, Calendar, LocalDate (J8)
	private LocalDate birthdate;
	private LocalDate deathdate;
	
	private List<Movie> directedMovies;
	
	public Artist() {
		super();
		this.directedMovies = new ArrayList<>();
	}
	
	public Artist(String name) {
		this(name, null, null);
	}
	
	public Artist(String name, LocalDate birthdate) {
		this(name, birthdate, null);
	}

	public Artist(String name, LocalDate birthdate, LocalDate deathdate) {
		this();
		this.name = name;
		this.birthdate = birthdate;
		this.deathdate = deathdate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable=false, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// @Temporal(TemporalType.DATE) // if types java.util.Date or java.util.Calendar
	@Column(nullable=true)
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	@Column(nullable=true)
	public LocalDate getDeathdate() {
		return deathdate;
	}
	public void setDeathdate(LocalDate deathdate) {
		this.deathdate = deathdate;
	}
	
	@OneToMany(mappedBy = "director")  // mapping configure in Movie property director
	public List<Movie> getDirectedMovies() {
		return directedMovies;
	}

	public void setDirectedMovies(List<Movie> directedMovies) {
		this.directedMovies = directedMovies;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append(name)
			.append("(")
			.append(birthdate)
			.append(", ")
			.append(deathdate)
			.append(")#")
			.append(id)
			.toString();
	}

}
