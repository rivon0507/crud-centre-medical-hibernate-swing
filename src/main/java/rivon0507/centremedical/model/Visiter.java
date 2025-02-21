package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Visiter {
    @Id @GeneratedValue private Long id;

    @Column private LocalDate date;

    @ManyToOne(targetEntity = Medecin.class, optional = false, cascade = CascadeType.REMOVE)
    private Medecin medecin;

    @ManyToOne(targetEntity = Patient.class, optional = false, cascade = CascadeType.REMOVE)
    private Patient patient;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Visiter && Objects.equals(id, ((Visiter) obj).id);
    }
}
