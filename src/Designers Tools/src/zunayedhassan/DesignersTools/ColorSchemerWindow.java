package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Zunayed Hassan
 */
public class ColorSchemerWindow extends Stage {
    public ColorSchemer CurrentColorSchemer = new ColorSchemer(this);
    
    public ColorSchemerWindow() {
        Scene scene = new Scene(this.CurrentColorSchemer, 800, 350);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Color Schemer");
        
        (new CommonTools()).LoadIcon(this, "icons/color-swatch.png");
    }
}
