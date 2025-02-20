package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.*;
import rivon0507.centremedical.enums.Sexe;
import java.util.Optional;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id @GeneratedValue private Long codepat;

    @Column(length = 20) private String nom;

    @Column(length = 50) private String prenom;

    @Column @Enumerated private Sexe sexe;

    @Column(length = 30) private String adresse;

    public String getNomEtPrenom() {
        return nom + Optional.ofNullable(prenom)
                .map(s -> " " + s)
                .orElse("");
    }
}
