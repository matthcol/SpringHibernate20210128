package movieapp.entity;

// POJO: Plain Old Java Object
// Bean
public class Movie {
	// implicit default constructor if none written

	private String title;
	private int year;
	private int duration;
	
	public Movie() {
		super();
	}

	public Movie(String title, int year, int duration) {
		super();
		this.title = title;
		this.year = year;
		this.duration = duration;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
			.append(" mn)")
			.toString();  // finalize String result
	}
	
}
