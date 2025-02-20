package rivon0507.centremedical.view.tablemodel;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.function.Function;

/// Base table model
///
/// @author Rivon
/// @see rivon0507.centremedical.model.Medecin
/// @see javax.swing.table.AbstractTableModel
/// @see javax.swing.table.TableModel
public abstract class BaseTableModel<T> extends AbstractTableModel {
    private final String[] columnNames;
    private final List<Function<T, Object>> columnMapping;
    private List<T> data;

    /// Constructs an `BaseTableModel` with a `Vector` as an initial data
    ///
    /// @param data the initial data
    /// @param columnNames the names of the columns
    /// @param columnMapping the function to get the value of the i-th column for an entity
    public BaseTableModel(List<T> data, String[] columnNames, List<Function<T, Object>> columnMapping) {
        super();
        this.data = data;
        this.columnNames = columnNames;
        this.columnMapping = columnMapping;
    }

    /**
     * Retrieves the object displayed in a specific row of the table model
     *
     * @param rowIndex said row's index
     * @return the object representing the row
     */
    public T getEntityAt(int rowIndex) {
        return data.get(rowIndex);
    }

    /**
     * Setter. Changes the data of the model
     *
     * @param data the new data
     */
    public void setData(List<T> data) {
        this.data = data;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T entity = getEntityAt(rowIndex);
        if (columnIndex < columnNames.length) {
            return columnMapping.get(columnIndex).apply(entity);
        }
        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }
}
