package uabc.taller.videoclubs.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.entidades.City;
import uabc.taller.videoclubs.repositorios.CityRepository;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;
    
    public List<City> findCityByCountryId(Integer countryId){
        return cityRepository.findCityByCountryId(countryId);
    }
    
	
	public List<City> buscarPorPais(Integer id) {
		return cityRepository.findByCountryCountryId(id);
	}

}
