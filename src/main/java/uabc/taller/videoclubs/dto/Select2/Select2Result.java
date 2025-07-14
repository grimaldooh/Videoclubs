package uabc.taller.videoclubs.dto.Select2;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Select2Result {

	private List<Select2Data> results;

	private Select2Pagination pagination;
}
