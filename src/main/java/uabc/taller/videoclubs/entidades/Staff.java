package uabc.taller.videoclubs.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(unique = true)
    private String email;

    @Column(name = "store_id")
    private Integer storeId;

    private Boolean active;

    private String username;

    private String password;

    @Column(name = "last_update")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private java.sql.Timestamp lastUpdate;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private Byte[] picture;

}
