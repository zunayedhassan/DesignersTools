package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Zunayed Hassan
 */
public class LinearGradientsWindow extends Stage {
    public LinearGradientDetailsPane CurrentLinearGradientDetailsPane = new LinearGradientDetailsPane(this);
    
    public LinearGradientsWindow() {
        Scene scene = new Scene(this.CurrentLinearGradientDetailsPane, 1350, 450);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Linear Gradient Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/linear-gradient-icon.png");
    }
}
