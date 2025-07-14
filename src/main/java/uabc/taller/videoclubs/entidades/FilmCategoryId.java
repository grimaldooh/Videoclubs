package uabc.taller.videoclubs.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class FilmCategoryId implements Serializable {

    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "category_id")
    private Integer categoryId;
}
