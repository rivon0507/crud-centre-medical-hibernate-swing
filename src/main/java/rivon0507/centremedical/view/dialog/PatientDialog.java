package rivon0507.centremedical.view.dialog;

import org.jetbrains.annotations.Nullable;
import rivon0507.centremedical.enums.Sexe;
import rivon0507.centremedical.model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PatientDialog extends EntityFormDialog<Patient> {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nomField;
    private JTextField prenomField;
    private JRadioButton masculinRadioButton;
    private JRadioButton femininRadioButton;
    private JTextField adresseField;

    public PatientDialog(Component parent, @Nullable Patient patient) {
        super(parent, Patient.class, patient);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(this::onOK);
        buttonCancel.addActionListener(this::onCancel);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(this::onCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        nomField.setText(getEntity().getNom());
        prenomField.setText(getEntity().getPrenom());
        adresseField.setText(getEntity().getAdresse());
        if (getEntity().getSexe() == Sexe.FEMININ) {
            femininRadioButton.setSelected(true);
        }
    }

    public PatientDialog(Component parent) {
        this(parent, null);
    }

    private void onOK(AWTEvent e) {
        String message = "Champs requis: \n";
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        Sexe sexe = masculinRadioButton.isSelected() ? Sexe.MASCULIN : femininRadioButton.isSelected() ? Sexe.FEMININ : null;
        message += nom.isBlank() ? "- Nom\n" : "";
        if (nom.isBlank()) {
            JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.WARNING_MESSAGE);
        } else {
            if (getOkListener() != null) {
                getOkListener().accept(Patient.builder().nom(nom).prenom(prenom).adresse(adresse).sexe(sexe).build());
            }
            dispose();
        }
    }

    private void onCancel(AWTEvent e) {
        dispose();
    }
}
