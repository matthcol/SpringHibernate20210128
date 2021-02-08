package movieapp.dto;

// DTO: Data Transfert Object
public class MovieStat {
	private Long count;
	private Integer minYear;
	private Integer maxYear;
	
	public MovieStat() {
		super();
	}

	public MovieStat(Long count, Integer minYear, Integer maxYear) {
		super();
		this.count = count;
		this.minYear = minYear;
		this.maxYear = maxYear;
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
