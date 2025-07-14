package uabc.taller.videoclubs.util;

import uabc.taller.videoclubs.dto.FilmDTO;
import uabc.taller.videoclubs.entidades.Film;

public class FilmConverter extends Converter<Film, FilmDTO> {
    @Override
    public Film fromDTO(FilmDTO filmDTO) {
        return null;
    }

    @Override
    public FilmDTO fromEntity(Film film) {
        FilmDTO dto = new FilmDTO();
        dto.setFilmId(film.getFilmId());
        dto.setTitle(film.getTitle());
        dto.setDescription(film.getDescription());
        dto.setLastUpdate(film.getLastUpdate());
        dto.setLength(film.getLength());
        dto.setRentalDuration(film.getRentalDuration());
        dto.setReplacementCost(film.getReplacementCost());
        dto.setRentalRate(film.getRentalRate());
        dto.setLanguage(film.getLanguage().getName());
        dto.setRating(film.getRating().getMpaa());
        return dto;
    }
}
