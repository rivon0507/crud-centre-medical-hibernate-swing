package rivon0507.centremedical.view.tablemodel;

import rivon0507.centremedical.model.Visiter;

import java.util.List;

public class VisiterTableModel extends BaseTableModel<Visiter> {
    public VisiterTableModel(List<Visiter> data) {
        super(
                data,
                new String[]{"Date", "Médecin", "Patient"},
                List.of(
                        Visiter::getDate,
                        visiter -> visiter.getMedecin().getNomEtPrenom(),
                        visiter -> visiter.getPatient().getNomEtPrenom()
                )
        );
    }
}
