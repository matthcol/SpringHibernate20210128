package movieapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

// POJO: Plain Old Java Object
// Bean
@Entity // persistance via une table
public class Movie {
	// implicit default constructor if none written

	private Integer id;
	private String title;
	private Integer year;
	private Integer duration;
	
	private String director;
	
	public Movie() {
		super();
	}

	public Movie(String title, Integer year, Integer duration) {
		super();
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
	
	@Transient
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		// return title + "(" + year + "," + duration + ")";
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
