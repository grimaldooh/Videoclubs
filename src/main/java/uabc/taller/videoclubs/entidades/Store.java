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

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private int storeId;

    @Column(name = "name")
    private String name;

    //relacion
    @Column(name = "manager_staff_id")
    private Integer managerStaffId;

    //relacion
    @Column(name = "address_id")
    private Integer addressId;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @OneToMany(mappedBy = "store")
    private List<Inventory> inventory;

    @JsonBackReference(value = "customer")
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Customer> customer = new ArrayList<>();
}
