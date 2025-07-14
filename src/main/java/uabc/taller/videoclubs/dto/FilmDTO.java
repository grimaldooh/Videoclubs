package uabc.taller.videoclubs.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmDTO {

	private Integer filmId;

	private String title;

	private String description;

	private Short rentalDuration;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "###,###.00")
	private BigDecimal rentalRate;

	private Short length;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "$###,###.00")
	private BigDecimal replacementCost;

	private String language;

	private String rating;

	private Timestamp lastUpdate;
}
