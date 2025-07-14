package uabc.taller.videoclubs.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer languageId;

    @Column(columnDefinition = "bpchar(20)", length = 20)
    private String name;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "last_update")
    private java.sql.Timestamp lastUpdate;
}
