package uabc.taller.videoclubs.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.TicketModel;
import uabc.taller.videoclubs.entidades.Customer;
import uabc.taller.videoclubs.entidades.Ticket;
import uabc.taller.videoclubs.repositorios.CustomerRepository;
import uabc.taller.videoclubs.repositorios.TicketRepository;

@Service
public class TicketService implements ITicketService{
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public void save(Ticket ticket) {
		ticketRepository.save(ticket);
	}
	
	@Override
	public List<TicketModel> findFromCustomerCustom(Integer customerId) {
		List<TicketModel> multas = new ArrayList<>();
		List<Ticket> tickets = ticketRepository.findFromCustomer(customerId);
		tickets.forEach(t -> {
			TicketModel multa = new TicketModel(t.getTicketId(), customerId, 
					t.getCustomer().getFirstName(), t.getCustomer().getLastName(), 
					t.getRental().getRentalId(), t.getRental().getRentalDate(), 
					t.getRental().getReturnDate(), t.getAmount(), t.getActive(), 
					t.getRental().getInventory().getFilm().getTitle());
			multas.add(multa);
		});
		
		return multas;
	}
	
	
	
	
	@Override
	public void pagar(Integer ticket,Map<String,Object> response) {
		try {
			Ticket objeto=ticketRepository.findByticketId(ticket);
			objeto.setActive(false);
			this.save(objeto);
			
			Customer customer= customerRepository.findByCustomerId(objeto.getCustomer().getCustomerId());
			if(!customer.getActiveBool()) {
				customer.setActiveBool(true);
				customerRepository.save(customer);
			}
			response.put("result", true);
			response.put("msj", "correcto");
			
			
		}catch (Exception e){
			response.put("result", false);
			response.put("msj", "error");
		}
	}
	
	
	@Override
	public Ticket consultarTicket(Integer ticketId) {
		
			return ticketRepository.findByticketId(ticketId);
	}
	
	
}
