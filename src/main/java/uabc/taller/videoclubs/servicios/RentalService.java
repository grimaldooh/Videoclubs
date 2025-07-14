package uabc.taller.videoclubs.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.CatalogoInventarioCliente;
import uabc.taller.videoclubs.dto.CatalogoInventarioPelicula;
import uabc.taller.videoclubs.dto.RentalDTO;
import uabc.taller.videoclubs.dto.ReturnIndex;
import uabc.taller.videoclubs.entidades.Customer;
import uabc.taller.videoclubs.entidades.Inventory;
import uabc.taller.videoclubs.entidades.Payment;
import uabc.taller.videoclubs.entidades.Rental;
import uabc.taller.videoclubs.entidades.Staff;
import uabc.taller.videoclubs.repositorios.PaymentRepository;
import uabc.taller.videoclubs.repositorios.RentalRepository;
import uabc.taller.videoclubs.repositorios.TicketRepository;

@Service
public class RentalService implements IRentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private CustomerService costumerService;
    
    @Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	TicketRepository ticketRepository;
    
	private RentalDTO toRentalDTO(Rental r){
		return RentalDTO.builder()
		.rentalId(r.getRentalId())
		.rentalDate(r.getRentalDate())
		.customerId(r.getCustomer().getCustomerId())
		.nombreCliente(String.format("%1$s %2$s", r.getCustomer().getFirstName(), r.getCustomer().getLastName()))
		.inventoryId(r.getInventory().getInventoryId())
		.tituloPelicula(r.getInventory().getFilm().getTitle())
		.returnDate(r.getReturnDate())
		.staffId(r.getStaff().getStaffId())
		.nombreStaff(String.format("%1$s %2$s", r.getStaff().getFirstName(), r.getStaff().getLastName()))
		.lastUpdate(r.getLastUpdate())
		.build();
	}
	
    @Override
    public List<ReturnIndex> returns(Integer id) {
        return rentalRepository.returns(id);
    }

    @Override
    public Optional<Rental> findById(Integer id) {
        return rentalRepository.findById(id);
    }
    
    public RentalDTO findByRentalId(Integer id) {
        return toRentalDTO(rentalRepository.findByRentalId(id));
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }
    
    public List<RentalDTO> findAllByUserStore(String user) {
    	Staff staff = userService.findByUserName(user);
        return rentalRepository.findAllBySotoreId(staff.getStoreId())
        		.stream().map(r->toRentalDTO(r)).collect(Collectors.toList());
    }
    
    public List<RentalDTO> findPendientesByUserStore(String user) {
    	Staff staff = userService.findByUserName(user);
        return rentalRepository.findPendientesBySotoreId(staff.getStoreId())
        		.stream().map(r->toRentalDTO(r)).collect(Collectors.toList());
    }
    
	public RentalDTO registrarRentaYPago(RentalDTO renta, String user) {
		try {
			Staff staff = userService.findByUserName(user);
			
			Inventory inventory = inventoryService.findByInventoryId(renta.getInventoryId());
			if(inventory == null)
				return null;
			
			Customer customer = costumerService.findByIdCustumer(renta.getCustomerId());
			if(customer == null)
				return null;

			Date hoy = new Date();
			Rental nrental = new Rental(new Timestamp(hoy.getTime()), inventory, customer, staff, new Timestamp(hoy.getTime()));
			nrental = rentalRepository.save(nrental);
			
			Payment payment = new Payment(customer, nrental, hoy, staff, inventory.getFilm().getRentalRate().doubleValue());
			paymentRepository.save(payment);
			renta = toRentalDTO(nrental);
			return renta;
		} catch (Exception e) {
			return null;
		}
	} 
	
	public List<RentalDTO> registrarRentasYPago(Integer customerId, List<RentalDTO> rentals, String user) {
		try {
			Staff staff = userService.findByUserName(user);
			if(rentals.size() == 0)
				return null;
			
			Customer customer = costumerService.findByIdCustumer(customerId);
			if(customer == null)
				return null;

			Date hoy = new Date();
			Timestamp now = new Timestamp(hoy.getTime());
			List<RentalDTO> nrentals =  new ArrayList();
			for(RentalDTO r : rentals) {
				Inventory inventory = inventoryService.findByInventoryId(r.getInventoryId());
				Rental nrental = new Rental(now, inventory, customer, staff, now);
				nrental = rentalRepository.save(nrental);
				Payment payment = new Payment(customer, nrental, hoy, staff, inventory.getFilm().getRentalRate().doubleValue());
				paymentRepository.save(payment);
				nrentals.add(toRentalDTO(nrental));
			}
			return nrentals;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<CatalogoInventarioCliente> buscarClientes(String search){
		String regex = "\\d+";
		List<CatalogoInventarioCliente> resultados = null;
		if(search.matches(regex)) {
			//ingreso un numero de cliente
			resultados = costumerService.findByCostumerId(Integer.parseInt(search));
		}else {
			//ingreso el nombre del cliente
			resultados = costumerService.filtrarClientePorNombreApellidoEmail(search);
		}
		return resultados;
	}
	
	public List<CatalogoInventarioPelicula> buscarPeliculas(String search, String usuario){
		String regex = "\\d+";
		List<CatalogoInventarioPelicula> resultados = null;
		if(search.matches(regex)) {
			//ingreso un numero de inventario
			resultados = inventoryService.findByInventory(Integer.parseInt(search));
		}else {
			//ingreso el titulo de la pelicula
			Staff staff =  userService.findByUserName(usuario);
			resultados = inventoryService.filtrarInventarioPorTiendaYTituloPelicula(staff.getStoreId(), search);
		}
		return resultados;
	}
	
	
}













