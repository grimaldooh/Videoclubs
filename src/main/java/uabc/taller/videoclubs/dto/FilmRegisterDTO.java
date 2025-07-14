package uabc.taller.videoclubs.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uabc.taller.videoclubs.entidades.MPAA;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmRegisterDTO {

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private Short releaseYear;

	@NotNull
	private Integer languageId;

	private Integer originalLanguageId;

	@NotNull
	private Short rentalDuration;

	@NotNull
	private Short length;

	@NotNull
	private BigDecimal rentalRate;

	@NotNull
	private BigDecimal replacementCost;

	@NotNull
	private MPAA rating;

	@NotNull
	private List<Integer> actorId;

	@NotNull
	private List<Integer> categoryId;

	@NotNull
	private List<String> specialFeatures;

	private Map<Integer, Integer> inventory;

	private String poster;
}
