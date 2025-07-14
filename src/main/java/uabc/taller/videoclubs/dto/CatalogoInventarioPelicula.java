package uabc.taller.videoclubs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface CatalogoInventarioPelicula {
	@JsonProperty("inventoryId")
	Integer getInventoryId();

	@JsonProperty("filmId")
	Integer getFilmId();

	@JsonProperty("title")
	String getTitle();

	@JsonProperty("description")
	String getDescription();

	@JsonProperty("releaseYear")
	Integer getReleaseYear();

	@JsonProperty("language")
	String getLanguage();

	@JsonProperty("rentalRate")
	String getRentalRate();

}
