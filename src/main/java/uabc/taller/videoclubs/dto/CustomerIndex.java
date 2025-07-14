package uabc.taller.videoclubs.dto;

public interface CustomerIndex {
	Integer getCustomerId();

	String getFirstName();

	String getLastName();

	default String getName() {
		return getFirstName().concat(" ").concat(getLastName());
	}

	String getEmail();
}
