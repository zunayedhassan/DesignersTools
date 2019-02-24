package zunayedhassan.DesignersTools;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Zunayed Hassan
 */
public class GradientDirectionLineHandle extends Circle {
    public static final double RADIUS = 5;
    
    public GradientDirectionLineHandle(DoubleProperty x, DoubleProperty y) {
        super(x.get(), y.get(), RADIUS, Color.WHITE);
        
        this.setStyle(
                "-fx-stroke: -fx-dark-text-color;" +
                "-fx-stroke-width: 0.5;"
        );
        
        this.setCursor(Cursor.HAND);
        
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle("-fx-stroke: #0083FF;");
            }
        });
        
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle("-fx-stroke: -fx-dark-text-color;");
            }
        });
        
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCenterX(event.getX());
                setCenterY(event.getY());
            }
        });
        
        this.centerXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number centerX) {
                x.set(centerX.doubleValue());
            }
        });
        
        this.centerYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number centerY) {
                y.set(centerY.doubleValue());
            }
        });
    }
}
