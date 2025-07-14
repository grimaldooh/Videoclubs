package uabc.taller.videoclubs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FilmPaginationDTO {
	private Integer page;
	private Integer totalPages;
	private List<CatalogoIndex> data;
}
