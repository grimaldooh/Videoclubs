package uabc.taller.videoclubs.entidades;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uabc.taller.videoclubs.util.YearConverter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "film")
public class Film {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id")
	private Integer filmId;

	private String title;

	private String description;

	@Column(name = "release_year", length = 0, columnDefinition = "year")
	@Convert(converter = YearConverter.class)
	private Year releaseYear;

	@Column(name = "rental_duration")
	private Short rentalDuration;

	@Column(name = "rental_rate", precision = 4, scale = 2, columnDefinition = "numeric(4,2)")
	private BigDecimal rentalRate;

	private Short length;

	@Column(name = "replacement_cost", precision = 5, scale = 2, columnDefinition = "numeric(5,2)")
	private BigDecimal replacementCost;

	@UpdateTimestamp
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "last_update")
	private java.sql.Timestamp lastUpdate;

	@ManyToOne
	@JoinColumn(name = "language_id")
	private Language language;

	@ManyToOne
	@JoinColumn(name = "original_language_id")
	private Language originalLanguage;

	@Enumerated(EnumType.STRING)
	@Column(name = "rating", columnDefinition = "mpaa_rating")
	@Type(type = "mpaa_rating_enum_type")
	private MPAA rating;

	@Column(name = "image")
	private byte[] image;

	// @ElementCollection
	// @Column(name = "special_features", columnDefinition = "_text")
	// @Column(name = "special_features", columnDefinition = "text[]")
	// @Convert(converter = TextListConverter.class)
	// private List<String> specialFeatures;
	// private String[] specialFeatures;

	@OneToMany(mappedBy = "film")
	private List<FilmCategory> filmCategories;

	@OneToMany(mappedBy = "film")
	private List<FilmActor> filmActors;

	@OneToMany(mappedBy = "film")
	private List<Inventory> inventories;

	public void setImage(String base64Data) {
		String base64Content = base64Data.split(",")[1];
		this.image = Base64.decodeBase64(base64Content);
	}

}
