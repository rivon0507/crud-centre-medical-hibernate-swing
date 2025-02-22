package rivon0507.centremedical.view.component;

import org.jetbrains.annotations.NotNull;
import rivon0507.centremedical.view.component.action.NamedAction;

import javax.swing.*;

public class PopupMenu extends JPopupMenu {
    public PopupMenu(NamedAction @NotNull ...actions) {
        super();
        for (NamedAction action : actions) {
            add(action);
        }
    }
}
