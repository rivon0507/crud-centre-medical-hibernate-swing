package rivon0507.centremedical.view;

import lombok.extern.slf4j.Slf4j;
import rivon0507.centremedical.repository.MedecinRepository;
import rivon0507.centremedical.repository.PatientRepository;
import rivon0507.centremedical.repository.VisiterRepository;
import rivon0507.centremedical.view.component.PopupMenu;
import rivon0507.centremedical.view.component.action.NamedAction;
import rivon0507.centremedical.view.dialog.MedecinDialog;
import rivon0507.centremedical.view.dialog.PatientDialog;
import rivon0507.centremedical.view.dialog.VisiterDialog;
import rivon0507.centremedical.view.tablemodel.MedecinTableModel;
import rivon0507.centremedical.view.tablemodel.PatientTableModel;
import rivon0507.centremedical.view.tablemodel.VisiterTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Slf4j
public class MainWindow extends JFrame {
    private final MedecinRepository medecinRepository = new MedecinRepository();
    private final PatientRepository patientRepository = new PatientRepository();
    private final VisiterRepository visiterRepository = new VisiterRepository();
    private JPanel contentPane;
    private JPanel medecinPanel;
    private JPanel patientPanel;
    private JPanel visiterPanel;
    private JTabbedPane tabbedPane;
    private JTable medecinTable;
    private JTable patientTable;
    private JTable visiterTable;
    private JButton addMedecinButton;
    private JButton addPatientButton;
    private JButton addVisiterButton;

    public MainWindow() throws HeadlessException {
        setTitle("Centre Medical");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setSize(new Dimension(800, 400));
        setLocationRelativeTo(null);
        tabbedPane.addChangeListener(this::onTabChange);
        addMedecinButton.addActionListener(this::onAddMedecinButtonClicked);
        addPatientButton.addActionListener(this::onAddPatientButtonClicked);
        addVisiterButton.addActionListener(this::onAddVisiteButtonClicked);
        setUpTables();
    }

    private void setUpTables() {
        medecinTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        visiterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medecinTable.setComponentPopupMenu(new PopupMenu(
                new NamedAction("Modifier", this::onModifierMedecin),
                new NamedAction("Supprimer", this::onSupprimerMedecin)
        ));
        patientTable.setComponentPopupMenu(new PopupMenu(
                new NamedAction("Modifier", this::onModifierPatient),
                new NamedAction("Supprimer", this::onSupprimerPatient)
        ));
        visiterTable.setComponentPopupMenu(new PopupMenu(
                new NamedAction("Modifier", this::onModifierVisiter),
                new NamedAction("Supprimer", this::onSupprimerVisiter)
        ));
        medecinTable.setModel(new MedecinTableModel(medecinRepository.findAll()));
        patientTable.setModel(new PatientTableModel(patientRepository.findAll()));
        visiterTable.setModel(new VisiterTableModel(visiterRepository.findAll()));
    }

    private void onAddVisiteButtonClicked(ActionEvent e) {
        VisiterDialog dialog = new VisiterDialog(medecinRepository.findAll(), patientRepository.findAll(), this);
        dialog.setAddVisiterListener(visiter -> {
            visiterRepository.save(visiter);
            ((VisiterTableModel) visiterTable.getModel()).setData(visiterRepository.findAll());
        });
        dialog.setVisible(true);
    }

    private void onAddPatientButtonClicked(ActionEvent e) {
        PatientDialog dialog = new PatientDialog(this);
        dialog.setAddPatientListener(patient -> {
            patientRepository.save(patient);
            ((PatientTableModel) patientTable.getModel()).setData(patientRepository.findAll());
        });
        dialog.setVisible(true);
    }

    private void onAddMedecinButtonClicked(ActionEvent e) {
        MedecinDialog dialog = new MedecinDialog(this);
        dialog.setAddMedecinListener(medecin -> {
            medecinRepository.save(medecin);
            ((MedecinTableModel) medecinTable.getModel()).setData(medecinRepository.findAll());
        });
        dialog.setVisible(true);
    }

    private void onTabChange(ChangeEvent e) {
        List<JButton> buttons = List.of(addMedecinButton, addPatientButton, addVisiterButton);
        List<JPanel> panels = List.of(medecinPanel, patientPanel, visiterPanel);
        for (int i = 0; i < 3; i++) {
            if (tabbedPane.getSelectedComponent() == panels.get(i)) {
                buttons.get(i).setMnemonic('A');
            } else {
                buttons.get(i).setMnemonic('\0');
            }
        }
    }

    private void onSupprimerVisiter(ActionEvent e) {
        // TODO
        log.info("Supprimer visiter selected");
    }

    private void onModifierVisiter(ActionEvent e) {
        // TODO
        log.info("Modifier visiter");
    }

    private void onSupprimerPatient(ActionEvent e) {
        // TODO
        log.info("Supprimer patient");
    }

    private void onModifierPatient(ActionEvent e) {
        // TODO
        log.info("Modifier patient selected");
    }

    private void onSupprimerMedecin(ActionEvent e) {
        // TODO
        log.info("Supprimer medecin");
    }

    private void onModifierMedecin(ActionEvent e) {
        // TODO
        log.info("User clicked on modifier medecin");
    }
}
