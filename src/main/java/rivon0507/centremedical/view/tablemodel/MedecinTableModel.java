package rivon0507.centremedical.view.tablemodel;

import rivon0507.centremedical.model.Medecin;

import java.util.List;

public class MedecinTableModel extends BaseTableModel<Medecin> {
    /**
     * Constructs an {@code BaseTableModel} with a {@code Vector} as an initial data
     *
     * @param data the initial data
     */
    public MedecinTableModel(List<Medecin> data) {
        super(
                data,
                new String[]{"Code", "Nom", "Prénom", "Grade"},
                List.of(Medecin::getId, Medecin::getNom, Medecin::getPrenom, Medecin::getGrade)
        );
    }
}
