package rivon0507.centremedical;

import lombok.extern.slf4j.Slf4j;
import rivon0507.centremedical.util.HibernateUtil;
import rivon0507.centremedical.view.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Slf4j
public class Main {
    public static void main(String[] args) {
        HibernateUtil.boot();

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    HibernateUtil.shutdown();
                }
            });
            window.setVisible(true);
        });
    }
}