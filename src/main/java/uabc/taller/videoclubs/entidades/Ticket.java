package uabc.taller.videoclubs.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Integer ticketId;

	@Column(name = "ticket_date")
	private Date ticketDate;
	       
	@OneToOne(fetch = FetchType.LAZY)    
	@JoinColumn(name="customer_id") 
	private Customer customer; 

	@OneToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name ="rental_id")
	private Rental rental;	
	
	@Column(name = "amount", precision = 5, scale = 2, columnDefinition = "numeric(5,2)")
	private Double amount; 
	
	private Boolean active;

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(Date ticketDate, Customer customer, Rental rental, Double amount, Boolean active) {
		super();
		this.ticketDate = ticketDate;
		this.customer = customer;
		this.rental = rental;
		this.amount = amount;
		this.active = active;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Rental getRental() {
		return rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", ticketDate=" + ticketDate + ", amount=" + amount + ", active="
				+ active + "]";
	}
	
}
