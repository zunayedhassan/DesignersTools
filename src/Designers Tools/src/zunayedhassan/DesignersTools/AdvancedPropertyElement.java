/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Zunayed Hassan
 */
public class AdvancedPropertyElement extends HBox {
    public static final double SIZE_WIDTH = 128;
    
    public Node ValueControl = null;
    public Label TitleLabel = new Label();
    
    public AdvancedPropertyElement(String title, Node control) {
        super(5);
        super.setAlignment(Pos.CENTER_LEFT);
        
        this.ValueControl = control;
        this.TitleLabel.setText(title);
        
        this.TitleLabel.setPrefWidth(SIZE_WIDTH);
        this.TitleLabel.setMinWidth(SIZE_WIDTH);
        
        this.getChildren().addAll(
            this.TitleLabel,
            this.ValueControl
        );
    }
}
