package zunayedhassan.DesignersTools;

import javafx.scene.control.Control;
import javafx.scene.control.Spinner;

/**
 *
 * @author Zunayed Hassan
 */
public class AdvancedPropertyElementWithSpinner extends AdvancedPropertyElement {
    public static double SIZE = 80;
    
    public Spinner<Integer> PropertySpinner = null;
    
    public AdvancedPropertyElementWithSpinner(String title, Control control) {
        super(title, control);
        
        this.PropertySpinner = (Spinner) control;
        this.PropertySpinner.setEditable(true);    
        
        control.setMaxWidth(SIZE);
    }
}
