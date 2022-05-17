package cz.inventi.kpj.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
public class KpjServiceEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String port;
    private OffsetDateTime registerTime;
}
