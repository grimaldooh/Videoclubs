package uabc.taller.videoclubs.dto.film_proyection;

public interface FilmActorP {
	String getFirstName();

	String getLastName();

	default String getName() {
		return getFirstName().concat(" ").concat(getLastName());
	}
}
