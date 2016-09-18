/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

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
