package rivon0507.centremedical.view.dialog;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

@Setter
@Getter
public abstract class EntityFormDialog<T> extends JDialog {
    private Consumer<T> okListener;
    private T entity;

    public EntityFormDialog(Component parent, Class<T> entityClass, T entity) {
        setLocationRelativeTo(parent);
        if (entity == null) {
            try {
                this.entity = entityClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.entity = entity;
        }
    }
}
