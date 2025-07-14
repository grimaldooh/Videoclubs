package uabc.taller.videoclubs.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uabc.taller.videoclubs.dto.CatalogoIndex;
import uabc.taller.videoclubs.dto.film_proyection.FilmP;
import uabc.taller.videoclubs.entidades.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {

	Optional<FilmP> findByFilmId(Integer id);

	@Query(value = "SELECT f.film_id AS filmId," + " f.title,"
			+ " (SELECT string_agg(c.name, ', ') AS category FROM category c, film_category fc WHERE c.category_id=fc.category_id AND fc.film_id=f.film_id GROUP BY fc.film_id) AS category,"
			+ " (SELECT string_agg(INITCAP(a.first_name || ' ' || a.last_name), ', ') AS actor FROM actor a, film_actor fa WHERE a.actor_id=fa.actor_id AND fa.film_id=f.film_id GROUP BY fa.film_id) AS actor,"
			+ " (SELECT COUNT(*) AS copies FROM inventory i WHERE i.film_id=f.film_id GROUP BY i.film_id) AS copies"
			+ " FROM film f" + " WHERE CASE WHEN ?1 = '' THEN true ELSE f.fulltext @@ to_tsquery(?1) END"
			+ " ORDER BY f.film_id LIMIT ?2 OFFSET ?3", nativeQuery = true)
	List<CatalogoIndex> buscadorPeliculas(String texto, Integer limit, Integer offset);

	@Query(value = "SELECT count(*) FROM film f WHERE CASE WHEN ?1 = '' THEN true ELSE f.fulltext @@ to_tsquery(?1) END", nativeQuery = true)
	Long countBuscadorPeliculas(String texto);

	@Query(value = "SELECT f.film_id AS filmId, f.title, (SELECT string_agg(c.name, ', ') AS category FROM category c, film_category fc WHERE c.category_id=fc.category_id AND fc.film_id=f.film_id GROUP BY fc.film_id) AS category, (SELECT string_agg(INITCAP(a.first_name || ' ' || a.last_name), ', ') AS actor FROM actor a, film_actor fa WHERE a.actor_id=fa.actor_id AND fa.film_id=f.film_id GROUP BY fa.film_id) AS actor, (SELECT COUNT(*) AS copies FROM inventory i WHERE i.film_id=f.film_id GROUP BY i.film_id) AS copies FROM film f", nativeQuery = true)
	List<CatalogoIndex> obtenerPeliculas();

}
