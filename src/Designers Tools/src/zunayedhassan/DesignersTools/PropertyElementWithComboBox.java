package zunayedhassan.DesignersTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Zunayed Hassan
 */
public class PropertyElementWithComboBox extends ComboBox<String> {    
    public ObservableList<String> List = null;
    
    public PropertyElementWithComboBox(String[] items, double width) {
        this.List = FXCollections.observableArrayList(items);
        super.setItems(this.List);
        super.setWidth(width);
    }
}
