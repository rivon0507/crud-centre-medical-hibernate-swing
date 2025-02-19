package rivon0507.centremedical.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rivon0507.centremedical.enums.Sexe;

@Entity @Getter @Setter
public class Patient {
    @Id @GeneratedValue private Long codepat;

    @Column(length = 20) private String nom;

    @Column(length = 30) private String prenom;

    @Column @Enumerated private Sexe sexe;

    @Column(length = 30) private String adresse;
}
