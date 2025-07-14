package uabc.taller.videoclubs.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "film_actor")
public class FilmActor {

    @EmbeddedId
    private FilmActorId id;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "last_update")
    private java.sql.Timestamp lastUpdate;

    public FilmActor(Film film, Actor actor) {
        this.id = new FilmActorId(film.getFilmId(), actor.getActorId());
        this.film = film;
        this.actor = actor;
    }
}
