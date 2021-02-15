package movieapp.persistence.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of") // uniqt non null attributes
@Builder
@ToString
@Document(collection = "movies")
public class Movie {
	@Getter @Setter @Id
	private String id;
	
	@NonNull @Getter @Setter
	private String title;
	
	@NonNull @Getter @Setter
	private Integer year;
	
	@Getter @Setter
	private Integer duration;
	
	@Getter @Setter
	private Artist director;
	
	@Getter @Setter
	private List<Artist> actors;
}
