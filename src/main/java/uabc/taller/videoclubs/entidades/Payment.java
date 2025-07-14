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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name ="payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Integer paymentId;
	
	@JsonIgnore       
	@OneToOne(fetch = FetchType.LAZY)    
	@JoinColumn(name="customer_id") 
	private Customer customer; 

	@JsonIgnore       
	@OneToOne(fetch = FetchType.LAZY)    
	@JoinColumn(name="rental_id") 
	private Rental rental; 

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "payment_date")
	private Date paymentDate;
	
	@JsonIgnore       
	@OneToOne(fetch = FetchType.LAZY)    
	@JoinColumn(name="staff_id") 
	private Staff staff; 

	@Column(name = "amount",columnDefinition = "NUMERIC(5,2)")
	private Double amount;

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(Customer customer, Rental rental, Date paymentDate, Staff staff, Double amount) {
		super();
		this.customer = customer;
		this.rental = rental;
		this.paymentDate = paymentDate;
		this.staff = staff;
		this.amount = amount;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
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

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Rental [paymentId=" + paymentId + ", customerId=" + customer + ", rental=" + rental
				+ ", paymentDate=" + paymentDate + ", staff=" + staff +", amount=" + amount+ "]";
	}
	
}
