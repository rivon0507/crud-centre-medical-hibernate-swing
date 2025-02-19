package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Visiter {
    @Id @GeneratedValue private Long id;

    @Column private LocalDate date;

    @ManyToOne(targetEntity = Medecin.class, optional = false)
    private Medecin medecin;

    @ManyToOne(targetEntity = Patient.class, optional = false)
    private Patient patient;
}
