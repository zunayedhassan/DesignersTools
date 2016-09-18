/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Zunayed Hassan
 */
public class ObjectPropertyElement extends BorderPane {
    public static final double SIZE = 50;
    
    public Label TitleLabel = null;
    public Slider PropertySlider = null;
    
    public ObjectPropertyElement(String title, double min, double max, double value) {
        this.TitleLabel = new Label(title);
        this.PropertySlider = new Slider(min, max, value);
        
        this.TitleLabel.setPrefWidth(SIZE);
        this.TitleLabel.setMinWidth(SIZE);
        this.TitleLabel.setMaxWidth(SIZE);
        
        this.PropertySlider.setPrefWidth(SIZE + 25);
        this.PropertySlider.setMinWidth(SIZE + 25);
        this.PropertySlider.setMaxWidth(SIZE + 25);

        this.setLeft(this.TitleLabel);
        this.setCenter(this.PropertySlider);
        this.setPadding(new Insets(0, 2.5, 2.5, 0));
    }
    
    public double GetValue() {
        return this.PropertySlider.valueProperty().get();
    }
}
