package uabc.taller.videoclubs.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReturnIndex {
	private Timestamp returnDate;
	private String title;

}
