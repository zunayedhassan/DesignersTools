package zunayedhassan.DesignersTools;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.LinearGradient;

/**
 *
 * @author Zunayed Hassan
 */
public class SampleLinearGradientButton extends Button {
    public static final double SIZE = 24;
    public LinearGradient SampleGradient = null;
    
    public SampleLinearGradientButton(String title, LinearGradient gradient) {
        SampleGradient = gradient;
        Rectangle rectangle = new Rectangle(SIZE, SIZE, gradient);
        super.setGraphic(rectangle);
        super.setCursor(Cursor.HAND);
        super.setTooltip(new Tooltip(title));
    }
}
