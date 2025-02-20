package rivon0507.centremedical.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import java.util.Optional;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Medecin {
    @Id @GeneratedValue private Long id;

    @Column(length = 20) private String nom;

    @Column(length = 50) private String prenom;

    @Column(length = 10) private String grade;

    public String getNomEtPrenom() {
        return nom + Optional.ofNullable(prenom)
                .map(s -> " " + s)
                .orElse("");
    }
}
