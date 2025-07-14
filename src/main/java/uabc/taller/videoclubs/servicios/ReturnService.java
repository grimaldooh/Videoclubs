package uabc.taller.videoclubs.servicios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.RentalDTO;
import uabc.taller.videoclubs.entidades.Customer;
import uabc.taller.videoclubs.entidades.Rental;
import uabc.taller.videoclubs.repositorios.RentalRepository;
import uabc.taller.videoclubs.repositorios.TicketRepository;
import uabc.taller.videoclubs.entidades.Ticket;

@Service
public class ReturnService implements IReturnService {
	
	@Autowired
	private RentalRepository rentalRepo;
	@Autowired
	private TicketRepository ticketRepo;
	@Autowired
	private CustomerService customerService;
	
	private RentalDTO toRentalDTO(Rental r) {
		return RentalDTO.builder()
		.rentalId(r.getRentalId())
		.rentalDate(r.getRentalDate())
		.customerId(r.getCustomer().getCustomerId())
		.nombreCliente(String.format("%1$s %2$s", r.getCustomer().getFirstName(),
				r.getCustomer().getLastName()))
		.inventoryId(r.getInventory().getInventoryId())
		.tituloPelicula(r.getInventory().getFilm().getTitle())
		.returnDate(r.getReturnDate())
		.staffId(r.getStaff().getStaffId())
		.nombreStaff(String.format("%1$s %2$s", r.getStaff().getFirstName(),
				r.getStaff().getLastName()))
		.lastUpdate(r.getLastUpdate())
		.build();
	}
	
	@Override
	public List<RentalDTO> obtenerPeliculasParaDevolver(Integer parametro){
		return rentalRepo.obtenerPeliculasParaDevolver(parametro)
				.stream().map(r->toRentalDTO(r)).collect(Collectors.toList());
				
	}
	
	@Override
	public List<RentalDTO> obtenerPeliculasDevueltas(Integer parametro){
		return rentalRepo.obtenerPeliculasDevueltas(parametro)
				.stream().map(r->toRentalDTO(r)).collect(Collectors.toList());
				
	}
	
	@Override
	public Integer obtenerDuracionRentaParaDevolver(Integer rentaId) {
		return rentalRepo.obtenerDuracionRentaParaDevolver(rentaId);
	}
	
	@Override
	public String registrarDevolucion(List<RentalDTO> rentals,  String returnDate, String multaGenerada, 
			Integer customerId) {
		String resultado = "OK";
		try {
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(RentalDTO renta : rentals) {
				rentalRepo.actualizarFechaEntrega(renta.getRentalId(), dateFormatter.parse(returnDate));
				if(multaGenerada!=null && !multaGenerada.equals("0.00")) {
					Double multa = Double.parseDouble(multaGenerada);
					multa = multa>999 ? 999 : multa;
					Ticket ticket = new Ticket(new Date(), new Customer(customerId), 
							new Rental(renta.getRentalId()), multa
							, true);
					ticketRepo.save(ticket);
					customerService.actualizarEstadoCliente(false, customerId);
				}
			}
		} catch (Exception e) {
			resultado = "ERROR";
		}
		
		return resultado;
	}
}
