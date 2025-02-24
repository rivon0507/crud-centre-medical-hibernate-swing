package rivon0507.centremedical.view;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import rivon0507.centremedical.model.Medecin;
import rivon0507.centremedical.model.Patient;
import rivon0507.centremedical.model.Visiter;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
    private JTextField patientSearchField;
    private JComboBox<String> patientSearchComboBox;

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
        patientSearchComboBox.addItemListener(this::patientSearchComboBoxItemStateChanged);
        setUpTables();
        patientSearchField.addActionListener(this::onPatientSearchFieldSearch);
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
        dialog.setOkListener(visiter -> {
            visiterRepository.save(visiter);
            refreshVisiterTable();
        });
        dialog.setVisible(true);
    }

    private void onAddPatientButtonClicked(ActionEvent e) {
        PatientDialog dialog = new PatientDialog(this);
        dialog.setOkListener(patient -> {
            patientRepository.save(patient);
            refreshPatientTable();
        });
        dialog.setVisible(true);
    }

    private void onAddMedecinButtonClicked(ActionEvent e) {
        MedecinDialog dialog = new MedecinDialog(this);
        dialog.setOkListener(medecin -> {
            medecinRepository.save(medecin);
            refreshMedecinTable();
        });
        dialog.setVisible(true);
    }

    private void onTabChange(ChangeEvent e) {
        List<JButton> buttons = List.of(addMedecinButton, addPatientButton, addVisiterButton);
        List<JPanel> panels = List.of(medecinPanel, patientPanel, visiterPanel);
        List<Consumer<?>> tableUpdateFunctions = List.of(
                _ -> ((MedecinTableModel) medecinTable.getModel()).setData(medecinRepository.findAll()),
                _ -> {
                    ((PatientTableModel) patientTable.getModel()).setData(patientRepository.findAll());
                    patientSearchField.setText("");
                },
                _ -> ((VisiterTableModel) visiterTable.getModel()).setData(visiterRepository.findAll())
        );
        for (int i = 0; i < 3; i++) {
            if (tabbedPane.getSelectedComponent() == panels.get(i)) {
                buttons.get(i).setMnemonic('A');
                tableUpdateFunctions.get(i).accept(null);
            } else {
                buttons.get(i).setMnemonic('\0');
            }
        }
    }

    private void onSupprimerVisiter(ActionEvent e) {
        int selected = visiterTable.convertRowIndexToModel(visiterTable.getSelectedRow());
        if (selected == -1) return;
        Visiter visiter = ((VisiterTableModel) visiterTable.getModel()).getEntityAt(selected);
        String message = "Voulez-vous vraiment supprimer cette visite ?";
        if (JOptionPane.showConfirmDialog(this, message, "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            visiterRepository.delete(visiter);
        }
        refreshVisiterTable();
    }

    private void onModifierVisiter(ActionEvent e) {
        int selected = visiterTable.convertRowIndexToModel(visiterTable.getSelectedRow());
        if (selected == -1) return;
        Visiter old = ((VisiterTableModel) visiterTable.getModel()).getEntityAt(selected);
        VisiterDialog dialog = new VisiterDialog(medecinRepository.findAll(), patientRepository.findAll(), this, old);
        dialog.setOkListener(visiter -> {
            visiter.setId(old.getId());
            visiterRepository.update(visiter);
            refreshVisiterTable();
        });
        dialog.setVisible(true);
    }

    private void refreshVisiterTable() {
        ((VisiterTableModel) visiterTable.getModel()).setData(visiterRepository.findAll());
    }

    private void onSupprimerPatient(ActionEvent e) {
        int selected = patientTable.convertRowIndexToModel(patientTable.getSelectedRow());
        if (selected == -1) return;
        Patient patient = ((PatientTableModel) patientTable.getModel()).getEntityAt(selected);
        String message = "Voulez-vous vraiment supprimer ce patient ?";
        message += !patient.getVisiters().isEmpty() ? "\n Ce patient a des visites associés. Ces visites seront également supprimées." : "";
        if (JOptionPane.showConfirmDialog(this, message, "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            patientRepository.delete(patient);
        }
        refreshPatientTable();
    }

    private void onModifierPatient(ActionEvent e) {
        int selected = patientTable.convertRowIndexToModel(patientTable.getSelectedRow());
        if (selected == -1) return;
        Patient old = ((PatientTableModel) patientTable.getModel()).getEntityAt(selected);
        PatientDialog dialog = new PatientDialog(this, old);
        dialog.setOkListener(patient -> {
            patient.setCodepat(old.getCodepat());
            patientRepository.update(patient);
            refreshPatientTable();
        });
        dialog.setVisible(true);
    }

    private void refreshPatientTable() {
        ((PatientTableModel) patientTable.getModel()).setData(patientRepository.findAll());
    }

    private void onSupprimerMedecin(ActionEvent e) {
        int selected = medecinTable.convertRowIndexToModel(medecinTable.getSelectedRow());
        if (selected == -1) return;
        Medecin medecin = ((MedecinTableModel) medecinTable.getModel()).getEntityAt(selected);
        String message = "Voulez-vous vraiment supprimer ce médecin ?";
        message += !medecin.getVisiters().isEmpty() ? "\n Ce médecin a des visites associés. Ces visites seront également supprimées." : "";
        if (JOptionPane.showConfirmDialog(this, message, "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            medecinRepository.delete(medecin);
        }
        refreshMedecinTable();
    }

    private void refreshMedecinTable() {
        ((MedecinTableModel) medecinTable.getModel()).setData(medecinRepository.findAll());
    }

    private void onModifierMedecin(ActionEvent e) {
        int selected = medecinTable.convertRowIndexToModel(medecinTable.getSelectedRow());
        if (selected == -1) return;
        Medecin old = ((MedecinTableModel) medecinTable.getModel()).getEntityAt(selected);
        MedecinDialog dialog = new MedecinDialog(this, old);
        dialog.setOkListener(medecin -> {
            medecin.setId(old.getId());
            medecinRepository.update(medecin);
            refreshMedecinTable();
        });
        dialog.setVisible(true);
    }

    private void patientSearchComboBoxItemStateChanged(@NotNull ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            patientSearchField.setToolTipText(e.getItem().toString() + " du patient");
            ((AbstractDocument) patientSearchField.getDocument()).setDocumentFilter(
                    "Code".equals(e.getItem().toString()) ? new NumericDocumentFilter() : new DocumentFilter()
            );
            patientSearchField.setText("");
            patientSearchField.grabFocus();
        }
    }

    private void onPatientSearchFieldSearch(ActionEvent e) {
        List<Patient> searchResult;
        if (Objects.equals(Objects.requireNonNull(patientSearchComboBox.getSelectedItem()).toString(), "Nom"))
            searchResult = patientRepository.findByNom(patientSearchField.getText());
        else searchResult = patientRepository.findById(Long.parseLong(patientSearchField.getText())).stream().toList();
        ((PatientTableModel) patientTable.getModel()).setData(searchResult);
    }

    private static class NumericDocumentFilter extends DocumentFilter {
        private static final String REGEX = "\\d*";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches(REGEX)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches(REGEX)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
