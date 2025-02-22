package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Medecin {
    @Id @GeneratedValue private Long id;

    @Column(length = 20) private String nom;

    @Column(length = 50) private String prenom;

    @Column(length = 10) private String grade;

    @OneToMany(targetEntity = Visiter.class, mappedBy = "medecin", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Visiter> visiters;

    public String getNomEtPrenom() {
        return nom + Optional.ofNullable(prenom)
                .map(s -> " " + s)
                .orElse("");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Medecin && Objects.equals(id, ((Medecin) obj).id);
    }
}
