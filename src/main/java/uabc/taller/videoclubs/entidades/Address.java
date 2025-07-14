package uabc.taller.videoclubs.entidades;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "address2")
    private String address2;
    
    @Column(name = "district")
    private String district;

    //relacion
    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "postal_code")
    private String postalCode;
    
    @Column(name = "phone")
    private String phone;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "last_update")
    private Timestamp lastUpdate;
    
    @JsonBackReference(value="customer")
	@OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Customer> customer = new ArrayList<>();

}
