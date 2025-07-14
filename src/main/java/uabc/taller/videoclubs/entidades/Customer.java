package uabc.taller.videoclubs.entidades;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;

	// relacion
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true)
	private String email;

	// relacion
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@Column(name = "activebool")
	private Boolean activeBool;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "create_date")
	private Date createDate;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "last_update")
	private Timestamp lastUpdate;

	@JsonBackReference(value = "rental")
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rental> rental = new ArrayList<>();

	@JsonBackReference(value = "ticket")
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ticket> ticket = new ArrayList<>();

	public Customer(int customerId) {
		super();
		this.customerId = customerId;
	}

	@Transient
	private Integer idCiudad;

	@Transient
	private Integer idTienda;

	@Column(name = "active")
	private Integer active;

}
