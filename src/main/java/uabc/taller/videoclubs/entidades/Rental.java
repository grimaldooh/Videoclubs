package uabc.taller.videoclubs.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "rental")
public class Rental {
   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "rental_date")
    private java.sql.Timestamp rentalDate;

//    @Column(name = "inventory_id")
//    private Integer inventoryId;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inventory_id")
    private Inventory inventory;

    //relacion
    //@Column(name = "customer_id")
    //private Integer customerId;
    @JsonBackReference
  	@OneToOne
  	@JoinColumn(name="customer_id")
  	private Customer customer;


    @Column(name = "return_date")
    private java.sql.Timestamp returnDate;

    @JsonBackReference
  	@OneToOne
  	@JoinColumn(name="staff_id")
  	private Staff staff;

    @Column(name = "last_update")
    private java.sql.Timestamp lastUpdate;

	public Rental(java.sql.Timestamp rentalDate, Inventory inventory, Customer customer, Staff staff, java.sql.Timestamp lastUpdate) {
		super();
		this.rentalDate = rentalDate;
		this.inventory = inventory;
		this.customer = customer;
		this.staff = staff;
		this.lastUpdate = lastUpdate;
	}

	public Rental(Integer rentalId) {
		super();
		this.rentalId = rentalId;
	}
    
     
}
