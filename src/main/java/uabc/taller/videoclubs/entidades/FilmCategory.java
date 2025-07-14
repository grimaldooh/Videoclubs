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
@Entity
@Builder
@Table(name = "film_category")
public class FilmCategory {

    @EmbeddedId
    private FilmCategoryId id;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "last_update")
    private java.sql.Timestamp lastUpdate;

    public FilmCategory(Film film, Category category) {
        this.id = new FilmCategoryId(film.getFilmId(), category.getId());
        this.film = film;
        this.category = category;
    }
}
