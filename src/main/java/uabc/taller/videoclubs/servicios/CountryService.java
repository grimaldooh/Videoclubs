package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.CountryIndex;
import uabc.taller.videoclubs.dto.CustomerIndex;
import uabc.taller.videoclubs.dto.DataTable;
import uabc.taller.videoclubs.dto.Paginacion;
import uabc.taller.videoclubs.entidades.Country;
import uabc.taller.videoclubs.entidades.Customer;
import uabc.taller.videoclubs.repositorios.CountryRepository;
import uabc.taller.videoclubs.util.CheckAvailability;

@Service
public class CountryService implements ICountryService{
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> findAll(){
        return countryRepository.findAll();
    }
    
    public String save(Country country) {
    	
    	List<Country>repetidos=countryRepository.findByName(country.getName());
    	
    	
    	if(repetidos.isEmpty()) {
        countryRepository.save(country);
        return "ok";
    	}
    	else {
    		return "duplicado";
    	}
    }

    public Country findByIdCountryDetalles(Integer id) {
        return countryRepository.findById(id).get();
    }
    
    
	
    
}
