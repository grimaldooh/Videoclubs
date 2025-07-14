package uabc.taller.videoclubs.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataTable {

	private Integer draw;
	private Integer start;
	private Integer length;
	private List<Map<String, String>> order;
	private Map<String, String> search;

	@JsonIgnore
	public Integer getPage() {
		return start == 0 ? start : Math.floorDiv(start, length);
	}
}
