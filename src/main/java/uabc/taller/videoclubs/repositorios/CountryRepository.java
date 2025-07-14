package uabc.taller.videoclubs.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import uabc.taller.videoclubs.dto.CountryIndex;
import uabc.taller.videoclubs.dto.CustomerIndex;
import uabc.taller.videoclubs.entidades.Country;
import uabc.taller.videoclubs.entidades.Customer;

public interface CountryRepository extends JpaRepository<Country, Integer>{

	Country save(Customer customer);
	
	
	List<Country> findByName(String name);
	
	
}
