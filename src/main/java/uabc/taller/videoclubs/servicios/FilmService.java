package uabc.taller.videoclubs.servicios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Year;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.CatalogoIndex;
import uabc.taller.videoclubs.dto.FilmDetails;
import uabc.taller.videoclubs.dto.FilmPaginationDTO;
import uabc.taller.videoclubs.dto.FilmRegisterDTO;
import uabc.taller.videoclubs.dto.film_proyection.FilmP;
import uabc.taller.videoclubs.entidades.Actor;
import uabc.taller.videoclubs.entidades.Category;
import uabc.taller.videoclubs.entidades.Film;
import uabc.taller.videoclubs.entidades.FilmActor;
import uabc.taller.videoclubs.entidades.FilmCategory;
import uabc.taller.videoclubs.entidades.Inventory;
import uabc.taller.videoclubs.entidades.Language;
import uabc.taller.videoclubs.entidades.Store;
import uabc.taller.videoclubs.repositorios.FilmActorRepository;
import uabc.taller.videoclubs.repositorios.FilmCategoryRepository;
import uabc.taller.videoclubs.repositorios.FilmRepository;
import uabc.taller.videoclubs.repositorios.FilmRepositoryImpl;
import uabc.taller.videoclubs.repositorios.InventoryRepository;
import uabc.taller.videoclubs.util.FilmDetailsToFilmPConverter;

@Service
public class FilmService implements IFilmService {

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private FilmRepositoryImpl filmRepositoryImpl;
	
	@Autowired
	private FilmActorRepository filmActorRepository;
	
	@Autowired
	private FilmCategoryRepository filmCategoryRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Optional<byte[]> findImageById(Integer id) {
		Optional<Film> f = filmRepository.findById(id);
		return f.map(Film::getImage);
	}

	@Override
	public String obtenerFormatoImagen(byte[] imagenData) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imagenData);
			ImageInputStream iis = ImageIO.createImageInputStream(bais);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (iter.hasNext()) {
				ImageReader reader = iter.next();
				return reader.getFormatName().toLowerCase();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "png";
	}

	@Override
	public FilmPaginationDTO buscadorPeliculas(String texto, Integer page) {
		Integer limit = 5;
		Integer offset = page * limit;

		List<CatalogoIndex> catalogo = filmRepository.buscadorPeliculas(texto.replace(" ", " & "), limit, offset);
		Long total = filmRepository.countBuscadorPeliculas(texto.replace(" ", " & "));
		Integer totalPages = (int) Math.ceil((double) total / (double) limit);
		return FilmPaginationDTO.builder().data(catalogo).page(page).totalPages(totalPages).build();
	}

	@Override
	public Optional<FilmDetails> findById(Integer id) {

		Optional<FilmP> f = filmRepository.findByFilmId(id);
		if (f.isPresent()) {
			FilmDetails fd = new FilmDetailsToFilmPConverter().fromEntity(f.get());
			fd.setSpecialFeatures(filmRepositoryImpl.getFilmSpecialFeatures(id));
			return Optional.of(fd);
		}
		return Optional.empty();
	}

	@Override
	public List<CatalogoIndex> findByOrder() {
		return filmRepository.obtenerPeliculas();
	}

	@Override
	public Boolean save(FilmRegisterDTO film) {
		
		Film filmE = new Film();
		filmE.setTitle(film.getTitle());
		filmE.setDescription(film.getDescription());
		filmE.setRentalRate(film.getRentalRate());
		filmE.setLength(film.getLength());
		filmE.setRating(film.getRating());
		filmE.setReleaseYear(Year.of(film.getReleaseYear()));
		filmE.setReplacementCost(film.getReplacementCost());
		filmE.setRentalDuration(film.getRentalDuration());
		filmE.setLanguage(Language.builder().languageId(film.getLanguageId()).build());
		filmE.setOriginalLanguage(Language.builder().languageId(film.getOriginalLanguageId()).build());
		
		if(film.getPoster() != null 
				&& !film.getPoster().isBlank() 
				&& !film.getPoster().isEmpty()) {
			filmE.setImage(film.getPoster());
		}
		
		try {
			Film saved = filmRepository.save(filmE);
			
			filmActorRepository.saveAll(film.getActorId().stream()
					.map(i -> new FilmActor(saved, Actor.builder()
							.actorId(i).build()))
					.collect(Collectors.toList()));
			
			filmCategoryRepository.saveAll(film.getCategoryId().stream()
					.map(i -> new FilmCategory(saved, Category.builder()
							.id(i).build()))
					.collect(Collectors.toList()));
			
			
			film.getInventory().forEach((key, value) -> inventoryRepository
					.saveAll(Stream.generate(() -> Inventory.builder()
							.film(saved)
					.store(Store.builder().storeId(key).build()).build())
					.limit(value).collect(Collectors.toList())));
			
			return Boolean.TRUE;
			
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			return Boolean.FALSE;
		}
		
		
		
		
		
	}
		
		
	
	
	
	
	
	
	
	
	
}
