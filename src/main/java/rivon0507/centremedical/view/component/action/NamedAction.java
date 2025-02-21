package rivon0507.centremedical.view.component.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NamedAction extends AbstractAction {
    private final ActionListener listener;

    public NamedAction(String name, ActionListener listener) {
        super(name);
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }
}

