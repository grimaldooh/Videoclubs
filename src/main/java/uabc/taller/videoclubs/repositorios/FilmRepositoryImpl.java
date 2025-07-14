package uabc.taller.videoclubs.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilmRepositoryImpl {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private JdbcTemplate jdbc;

	public void updateFilmSpecialFeatures(Integer id, String features) {

		try {
			jdbc.update("UPDATE public.film SET special_features=" + features + " WHERE film_id=?", id);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

	}

	public List<String> getFilmSpecialFeatures(Integer id) {

		try {
			return jdbc.queryForList("SELECT unnest(f.special_features) AS value FROM film f where f.film_id=?",
					String.class, id);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return new ArrayList<>();
	}
}
