package uabc.taller.videoclubs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Paginacion {

	private Integer draw;
	private Long recordsTotal;
	private Long recordsFiltered;
	private List<List<String>> data;
}
