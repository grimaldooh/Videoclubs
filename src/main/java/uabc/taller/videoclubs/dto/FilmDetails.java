package uabc.taller.videoclubs.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uabc.taller.videoclubs.dto.film_proyection.FilmActorsP;
import uabc.taller.videoclubs.dto.film_proyection.FilmCategoriesP;
import uabc.taller.videoclubs.entidades.MPAA;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmDetails {

	private Integer filmId;

	private String title;

	private String description;

	private Integer releaseYear;

	private Short rentalDuration;

	private BigDecimal rentalRate;

	private Short length;

	private Integer replacementCost;

	private LanguageP language;

	private List<FilmCategoriesP> filmCategories;

	private List<FilmActorsP> filmActors;

	private MPAA rating;

	private Timestamp lastUpdate;

	private List<String> specialFeatures;
}
