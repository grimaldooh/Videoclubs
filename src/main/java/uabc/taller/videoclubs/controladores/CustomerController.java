package uabc.taller.videoclubs.controladores;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;

import uabc.taller.videoclubs.dto.DataTable;
import uabc.taller.videoclubs.dto.Paginacion;
import uabc.taller.videoclubs.entidades.City;
import uabc.taller.videoclubs.entidades.Customer;
import uabc.taller.videoclubs.servicios.AddressService;
import uabc.taller.videoclubs.servicios.CityService;
import uabc.taller.videoclubs.servicios.CountryService;
import uabc.taller.videoclubs.servicios.CustomerService;
import uabc.taller.videoclubs.servicios.StoreService;

@Controller
@RequestMapping("customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CityService cityService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private StoreService storeService;

	private HashMap<String, Object> response(Boolean res, Object data, String message) {
        HashMap<String, Object> _response = new HashMap<>();
        _response.put("response", res);
        _response.put("message", message);
        _response.put("data", data);
        return _response;
    }

	@RequestMapping()
	public String index(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("sucursal", storeService.findAll());
		model.addAttribute("customer", new Customer());
		return "views/customers/customers";
	}

	@GetMapping("list")
	public ResponseEntity<Paginacion> list(DataTable dataTable) {

		Paginacion p = customerService.getCustomers(dataTable);

		return ResponseEntity.ok(p);
	}

	/*@PostMapping(value = "createCustomer")
	public String registro(Model model, @RequestParam(name = "selectCountry") Integer country,
			RedirectAttributes redirectAtt) {
		return "redirect:/";
	}*/

	// @GetMapping("filtroCiudad/{countryId}")
	// public String filtroCiudad(@PathVariable Integer countryId) {
	// List<City> cities = cityService.findCityByCountryId(countryId);
	// return "redirect:/";
	// }

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "ciudades/{pais}")
	@ResponseBody
	public List<Map> cargar(@PathVariable Integer pais) {
		List<City> ciudades = cityService.buscarPorPais(pais);
		List<Map> c = ciudades.stream()
				.map(item -> new HashMap<String, Object>(Map.of("id", item.getCityId(), "text", item.getName())))
				.collect(Collectors.toList());
		return c;
	}

	@RequestMapping(value = { "/registercustomer", "registercustomer" })
	public String registerCustomer(Model model) {

		return "views/registercustomer";
	}

	@RequestMapping("detallesCustomer/{id}")
    @ResponseBody
    public HashMap<String, Object> rentalId(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal, @PathVariable Integer id) {
        if (id == null)
            return response(false, null, "Datos invalidos");

        String customer = customerService.findByIdCustumerDetalles(id);
        return response(customer != null, customer, customer == null ? "Registro no encontrado" : "");
    }

	// @PostMapping(value = "/register")
	// @ResponseBody
	// public String registrarSolicitud(Model model, @RequestParam(name = "prueba")
	// Integer prueba) {
	//
	//
	// return "ok";
	// }

	@PostMapping(value = "register")
	@ResponseBody
	public String registroCliente(
			@ModelAttribute(name = "customer") @Valid Customer customer,
			BindingResult bindingResult) {

		Date fecha = new Date();
		Timestamp fechaStamp = new Timestamp(fecha.getTime());
		long timeInMilliSeconds = fecha.getTime();
		java.sql.Date fechaSql = new java.sql.Date(timeInMilliSeconds);

		customer.getAddress().setCityId(customer.getIdCiudad());
		customer.getAddress().setLastUpdate(fechaStamp);

		customer.setAddress(addressService.save(customer.getAddress()));
		customer.setStore(storeService.obtenerTiendaPorId(customer.getIdTienda()));
		customer.setActiveBool(true);
		customer.setActive(1);
		customer.setCreateDate(fechaSql);
		customer.setLastUpdate(fechaStamp);

		try {
			customerService.save(customer);
			return "ok";
		} catch (Exception e) {
			return "no OK";
		}
		
	}

}
