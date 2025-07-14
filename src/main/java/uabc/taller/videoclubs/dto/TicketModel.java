package uabc.taller.videoclubs.dto;

import java.util.Date;

public class TicketModel {
	private Integer ticketId;

	private Integer customerId;

	private String customerFirstName;

	private String customerLastName;

	private Integer rentalId;

	private Date rentalDate;

	private Date returnDate;

	private Double amount;

	private Boolean active;

	private String filmTitle;

	public TicketModel(Integer ticketId, Integer customerId, String customerFirstName, String customerLastName,
			Integer rentalId, Date rentalDate, Date returnDate, Double amount, Boolean active, String filmTitle) {
		super();
		this.ticketId = ticketId;
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.rentalId = rentalId;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.amount = amount;
		this.active = active;
		this.filmTitle = filmTitle;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public Integer getRentalId() {
		return rentalId;
	}

	public void setRentalId(Integer rentalId) {
		this.rentalId = rentalId;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	public String getFilmTitle() {
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	@Override
	public String toString() {
		return "TicketModel [ticketId=" + ticketId + ", customerId=" + customerId + ", customerFirstName="
				+ customerFirstName + ", customerLastName=" + customerLastName + ", rentalId=" + rentalId
				+ ", rentalDate=" + rentalDate + ", returnDate=" + returnDate + ", amount=" + amount + ", active="
				+ active + ", filmTitle=" + filmTitle + "]";
	}

}
