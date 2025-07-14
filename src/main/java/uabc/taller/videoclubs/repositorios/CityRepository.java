package uabc.taller.videoclubs.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uabc.taller.videoclubs.entidades.City;


public interface CityRepository extends JpaRepository<City, Integer>{
    @Query(value ="select c.city_id, c.city from city c where c.country_id=?1" , nativeQuery=true)
    List<City> findCityByCountryId(Integer countryId);
    
	List<City> findByCountryCountryId(Integer id);

    
}


