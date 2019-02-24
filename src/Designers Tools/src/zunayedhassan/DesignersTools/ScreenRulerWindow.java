package zunayedhassan.DesignersTools;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Zunayed Hassan
 */
public class ScreenRulerWindow extends Stage {
    public ScreenRuler CurrentScreenRuler = new ScreenRuler(this);
    
    private Point2D _initialMousePosition = null;
    
    public ScreenRulerWindow() {
        Scene scene = new Scene(this.CurrentScreenRuler, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        scene.setFill(Color.TRANSPARENT);
        
        this.initStyle(StageStyle.TRANSPARENT);
        this.setScene(scene);
        this.setTitle("Screen Ruler");
        
        (new CommonTools()).LoadIcon(this, "icons/ruler-y.png");
        
        // Events
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _initialMousePosition = new Point2D(
                        getX() - event.getScreenX(),
                        getY() - event.getScreenY()
                );
                
                event.consume();
            }
        });
        
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setX(event.getScreenX() + _initialMousePosition.getX());
                setY(event.getScreenY() + _initialMousePosition.getY());
            }
        });
    }
}
