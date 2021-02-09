package movieapp.dto;

public class MovieSimple {
	private Integer id;
	private String title;
	private Integer year;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "MovieSimple [id=" + id + ", title=" + title + ", year=" + year + "]";
	}
	
}
