package movieapp.persistence.document;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Artist {
	@NonNull @Getter @Setter
	private String name;
	
	@Getter @Setter
	private LocalDate birthdate;
}
