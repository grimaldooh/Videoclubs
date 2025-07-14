package uabc.taller.videoclubs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RentalDTO {
	private Integer rentalId;

	@JsonFormat(locale = "es_MX", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a", timezone = "America/Tijuana")
	private java.sql.Timestamp rentalDate;
	private Integer inventoryId;
	private Integer customerId;

	@JsonFormat(locale = "es_MX", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a", timezone = "America/Tijuana")
	private java.sql.Timestamp returnDate;
	private Integer staffId;

	@JsonFormat(locale = "es_MX", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a", timezone = "America/Tijuana")
	private java.sql.Timestamp lastUpdate;

	private String nombreCliente;
	private String tituloPelicula;
	private String nombreStaff;
}
