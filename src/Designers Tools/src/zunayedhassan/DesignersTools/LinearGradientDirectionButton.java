/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static zunayedhassan.DesignersTools.LinearGradientDetailsPane.GRADIENT_DIRECTION_PANE_SIZE;


/**
 *
 * @author Zunayed Hassan
 */
public class LinearGradientDirectionButton extends Button {
    public Point2D Point1 = null;
    public Point2D Point2 = null;
    
    public LinearGradientDirectionButton(String icon, double x1, double y1, double x2, double y2, GradientDirectionLineHandle handle1, GradientDirectionLineHandle handle2) {
        super.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream(icon))));
        
        this.Point1 = new Point2D(x1, y1);
        this.Point2 = new Point2D(x2, y2);
        
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handle1.centerXProperty().set(Point1.getX() * GRADIENT_DIRECTION_PANE_SIZE);
                handle1.centerYProperty().set(Point1.getY() * GRADIENT_DIRECTION_PANE_SIZE);
                handle2.centerXProperty().set(Point2.getX() * GRADIENT_DIRECTION_PANE_SIZE);
                handle2.centerYProperty().set(Point2.getY() * GRADIENT_DIRECTION_PANE_SIZE);
            }
        });
    }
}
