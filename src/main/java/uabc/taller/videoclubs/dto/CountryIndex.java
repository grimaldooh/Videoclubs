package uabc.taller.videoclubs.dto;

public interface CountryIndex {

	Integer getCountryId();

	

	String getCountryName();

	default String getName() {
		return getCountryName();
	}

}
