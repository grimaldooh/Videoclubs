package uabc.taller.videoclubs.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uabc.taller.videoclubs.entidades.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	@Query(value="SELECT t FROM Ticket t "
			+ "INNER JOIN FETCH t.customer c "
			+ "INNER JOIN FETCH c.store s "
			+ "INNER JOIN FETCH c.address a "
			+ "INNER JOIN FETCH t.rental r "
			+ "INNER JOIN FETCH r.inventory i "
			+ "INNER JOIN FETCH i.film f "
			+ "WHERE c.id = ?1")
	public List<Ticket> findFromCustomer(Integer customerId);
	
	@Query(value="SELECT t FROM Ticket t "
			+ "INNER JOIN FETCH t.customer c "
			+ "INNER JOIN FETCH t.rental r "
			+ "INNER JOIN FETCH r.inventory i "
			+ "INNER JOIN FETCH i.film f "
			+ "WHERE t.ticketId = ?1")
	public Ticket findByticketId(Integer ticketId);
}
