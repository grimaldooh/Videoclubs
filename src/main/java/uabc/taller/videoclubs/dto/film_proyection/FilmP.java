package uabc.taller.videoclubs.dto.film_proyection;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
import java.util.List;

import uabc.taller.videoclubs.dto.LanguageP;
import uabc.taller.videoclubs.entidades.MPAA;

public interface FilmP {

	Integer getFilmId();

	String getTitle();

	String getDescription();

	Year getReleaseYear();

	Short getRentalDuration();

	BigDecimal getRentalRate();

	Short getLength();

	Integer getReplacementCost();

	LanguageP getLanguage();

	List<FilmCategoriesP> getFilmCategories();

	List<FilmActorsP> getFilmActors();

	MPAA getRating();

	Timestamp getLastUpdate();
}
