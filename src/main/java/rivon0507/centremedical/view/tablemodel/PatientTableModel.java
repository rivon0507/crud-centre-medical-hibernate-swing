package rivon0507.centremedical.view.tablemodel;

import rivon0507.centremedical.model.Patient;

import java.util.List;

public class PatientTableModel extends BaseTableModel<Patient> {
    public PatientTableModel(List<Patient> data) {
        super(
                data,
                new String[]{"Code", "Nom", "Prenom", "Sexe", "Adresse"},
                List.of(Patient::getCodepat, Patient::getNom, Patient::getPrenom, Patient::getSexe, Patient::getAdresse)
        );
    }
}
