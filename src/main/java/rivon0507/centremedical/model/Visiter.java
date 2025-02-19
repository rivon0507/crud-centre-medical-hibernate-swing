package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
public class Visiter {
    @Id @GeneratedValue private Long id;

    @Column private LocalDate date;

    @ManyToOne(targetEntity = Medecin.class, optional = false)
    private Medecin medecin;

    @ManyToOne(targetEntity = Patient.class, optional = false)
    private Patient patient;
}
