package uabc.taller.videoclubs.util;

import uabc.taller.videoclubs.dto.FilmDetails;
import uabc.taller.videoclubs.dto.film_proyection.FilmP;

public class FilmDetailsToFilmPConverter extends Converter<FilmP, FilmDetails> {

    @Override
    public FilmP fromDTO(FilmDetails filmDetails) {
        return null;
    }

    @Override
    public FilmDetails fromEntity(FilmP film) {
        FilmDetails dto = new FilmDetails();
        dto.setFilmId(film.getFilmId());
        dto.setTitle(film.getTitle());
        dto.setDescription(film.getDescription());
        dto.setReleaseYear(film.getReleaseYear().getValue());
        dto.setLanguage(film.getLanguage());
        dto.setRating(film.getRating());
        dto.setRentalDuration(film.getRentalDuration());
        dto.setRentalRate(film.getRentalRate());
        dto.setFilmActors(film.getFilmActors());
        dto.setFilmCategories(film.getFilmCategories());
        dto.setLastUpdate(film.getLastUpdate());
        dto.setLength(film.getLength());
        dto.setReplacementCost(film.getReplacementCost());
        return dto;
    }
}
