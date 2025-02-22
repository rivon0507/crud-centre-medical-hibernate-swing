package rivon0507.centremedical.view.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import org.jetbrains.annotations.Nullable;
import rivon0507.centremedical.model.Medecin;
import rivon0507.centremedical.model.Patient;
import rivon0507.centremedical.model.Visiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class VisiterDialog extends EntityFormDialog<Visiter> {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<Patient> patientComboBox;
    private JComboBox<Medecin> medecinComboBox;
    private DatePicker datePicker;

    public VisiterDialog(List<Medecin> medecins, List<Patient> patients, Component parent, @Nullable Visiter visiter) {
        super(parent, Visiter.class, visiter);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(this::onOK);
        buttonCancel.addActionListener(this::onCancel);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(this::onCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        medecinComboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(medecins)));
        medecinComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Medecin medecin) {
                    setText(medecin.getNomEtPrenom());
                }
                return this;
            }
        });
        patientComboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(patients)));
        patientComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Patient patient) {
                    setText(patient.getNomEtPrenom());
                }
                return this;
            }
        });
        datePicker.setDate(getEntity().getDate());
        patientComboBox.setSelectedItem(getEntity().getPatient());
        medecinComboBox.setSelectedItem(getEntity().getMedecin());
        pack();
    }

    public VisiterDialog(List<Medecin> medecins, List<Patient> patients, Component parent) {
        this(medecins, patients, parent, null);
    }

    private void onOK(AWTEvent e) {
        String message = "Champs requis: \n";
        Medecin medecin = (Medecin) medecinComboBox.getSelectedItem();
        Patient patient = (Patient) patientComboBox.getSelectedItem();
        LocalDate date = datePicker.getDate();
        message += medecin == null ? "- Médecin\n" : "";
        message += patient == null ? "- Patient\n" : "";
        message += date == null ? "- Date\n" : "";
        if (medecin == null || patient == null || date == null) {
            JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.WARNING_MESSAGE);
        } else {
            if (getOkListener() != null) {
                getOkListener().accept(Visiter.builder().medecin(medecin).patient(patient).date(date).build());
            }
            dispose();
        }
    }

    private void onCancel(AWTEvent e) {
        dispose();
    }
}
