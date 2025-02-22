package rivon0507.centremedical.view.dialog;

import org.jetbrains.annotations.Nullable;
import rivon0507.centremedical.model.Medecin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MedecinDialog extends EntityFormDialog<Medecin> {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField gradeTextField;

    public MedecinDialog(Component parent) {
        this(parent, null);
    }

    public MedecinDialog(Component parent, @Nullable Medecin medecin) {
        super(parent, Medecin.class, medecin);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(this::onOK);
        buttonCancel.addActionListener(this::onCancel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        // call onCancel() when cross is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(e);
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(this::onCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        nomField.setText(getEntity().getNom());
        prenomField.setText(getEntity().getPrenom());
        gradeTextField.setText(getEntity().getGrade());
    }

    private void onCancel(AWTEvent e) {
        dispose();
    }

    private void onOK(AWTEvent e) {
        String message = "Champs requis: \n";
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String grade = gradeTextField.getText();
        message += nom.isBlank() ? "- Nom\n" : "";
        message += grade.isBlank() ? "- Grade\n" : "";
        if (nom.isBlank() || grade.isBlank()) {
            JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.WARNING_MESSAGE);
        } else {
            if (getOkListener() != null) {
                getOkListener().accept(Medecin.builder().nom(nom).prenom(prenom).grade(grade).build());
            }
            dispose();
        }
    }
}
