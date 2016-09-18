/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

/**
 *
 * @author ZUNAYED_PC
 */
public class PropertyElement extends HBox {
    public static final double STEP = 1;
    
    public Label PropertyTitleLabel = null;
    public Slider PropertySlider = null;
    public Spinner<Integer> PropertySpinner = null;
    public DoubleProperty PropertyValue = null;
    
    public PropertyElement(String title, DoubleProperty property, int min, int max, int value) {
        this.PropertyValue = property;
        this.setAlignment(Pos.CENTER_LEFT);
        
        this.PropertyTitleLabel = new Label(title);
        this.PropertyTitleLabel.setMinWidth(100);
        this.PropertyTitleLabel.setPrefWidth(100);
        
        this.PropertySlider = new Slider((double) min, (double) max, (double) value);
        this.PropertySpinner = new Spinner<>(min, max, value);
        this.PropertySpinner.setMaxWidth(80);
        
        DoubleProperty spinnerValueProperty = new SimpleDoubleProperty(value);
        
        this.PropertySpinner.getValueFactory().valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                spinnerValueProperty.set(newValue);
            }
        });
        
        spinnerValueProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                PropertySpinner.getValueFactory().setValue(newValue.intValue());
            }
        });
        
        this.PropertyValue.bindBidirectional(this.PropertySlider.valueProperty());
        this.PropertyValue.bindBidirectional(spinnerValueProperty);
        
        this.getChildren().addAll(
                this.PropertyTitleLabel,
                this.PropertySlider,
                this.PropertySpinner
        );
    }
}
