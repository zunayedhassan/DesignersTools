package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ZUNAYED_PC
 */
public class RadialGradientWindow extends Stage {
    public RadialGradientDetailsPane CurrentRadialGradientDetailsPane = new RadialGradientDetailsPane(this);
    
    public RadialGradientWindow() {
        Scene scene = new Scene(this.CurrentRadialGradientDetailsPane, 1350, 450);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Radial Gradient Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/radial-gradient-icon.png");
    }
}
