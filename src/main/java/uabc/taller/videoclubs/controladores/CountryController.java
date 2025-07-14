package uabc.taller.videoclubs.controladores;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uabc.taller.videoclubs.dto.DataTable;
import uabc.taller.videoclubs.dto.Paginacion;
import uabc.taller.videoclubs.dto.RentalDTO;
import uabc.taller.videoclubs.entidades.Country;

import uabc.taller.videoclubs.servicios.CountryService;

@Controller
@RequestMapping("paises")
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	@RequestMapping()
	public String index(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
		
		
		return "views/paises/paises";
	}
	
	@PostMapping(value = "register")
	@ResponseBody
	public String registroPais(
			@RequestParam String registroPais) {

		Date fecha = new Date();
		Timestamp fechaStamp = new Timestamp(fecha.getTime());
		long timeInMilliSeconds = fecha.getTime();
		java.sql.Date fechaSql = new java.sql.Date(timeInMilliSeconds);
		
		Country country= new Country();
		
		registroPais=registroPais.toUpperCase();
		
		
		country.setName(registroPais);
		
	
		country.setLastUpdate(fechaStamp);
		

		
		//country.setLastUpdate(fechaStamp);


		try {
			return countryService.save(country);
		} catch (Exception e) {
			return "no OK";
		}
		
	}
	
	@GetMapping("list2")
	@ResponseBody
	public List<Country> list(Model model, HttpServletRequest request, HttpServletResponse response) {

		List<Country> res= countryService.findAll();

		return res;
	}
	
	@RequestMapping("list")
    @ResponseBody
    public HashMap<String, Object> consultaPendientes(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        
        List<Country> res= countryService.findAll();
        return response(true, res, "");
    }
	
	private HashMap<String, Object> response(Boolean res, Object data, String message) {
        HashMap<String, Object> _response = new HashMap<>();
        _response.put("response", res);
        _response.put("message", message);
        _response.put("data", data);
        return _response;
    }
	
	@RequestMapping("detallesCountry/{id}")
    @ResponseBody
    public HashMap<String, Object> rentalId(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal, @PathVariable Integer id) {
        if (id == null)
            return response(false, null, "Datos invalidos");

        Country country = countryService.findByIdCountryDetalles(id);
        return response(country != null, country, country == null ? "Registro no encontrado" : "");
    }
	
	
	
	
	
	
	

}

