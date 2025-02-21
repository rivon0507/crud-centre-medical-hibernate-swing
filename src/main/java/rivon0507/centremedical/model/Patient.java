package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.*;
import rivon0507.centremedical.enums.Sexe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id @GeneratedValue private Long codepat;

    @Column(length = 20) private String nom;

    @Column(length = 50) private String prenom;

    @Column @Enumerated private Sexe sexe;

    @Column(length = 30) private String adresse;

    @OneToMany(targetEntity = Visiter.class, mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Visiter> visiters;

    public String getNomEtPrenom() {
        return nom + Optional.ofNullable(prenom)
                .map(s -> " " + s)
                .orElse("");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Patient && Objects.equals(codepat, ((Patient) obj).codepat);
    }
}
