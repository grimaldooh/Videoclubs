package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.Optional;

import uabc.taller.videoclubs.dto.CatalogoIndex;
import uabc.taller.videoclubs.dto.FilmDetails;
import uabc.taller.videoclubs.dto.FilmPaginationDTO;
import uabc.taller.videoclubs.dto.FilmRegisterDTO;

public interface IFilmService {

	Optional<byte[]> findImageById(Integer id);

	String obtenerFormatoImagen(byte[] imagenData);

	FilmPaginationDTO buscadorPeliculas(String texto, Integer page);

	Optional<FilmDetails> findById(Integer id);

	List<CatalogoIndex> findByOrder();
	
	Boolean save(FilmRegisterDTO film);

}
