package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Zunayed Hassan
 */
class BoxShadowWindow extends Stage {
    public BoxShadow CurrentBoxShadow = new BoxShadow(this);
    
    public BoxShadowWindow() {
        Scene scene = new Scene(this.CurrentBoxShadow, 700, 350);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Box Shaow Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/image-shadow.png");
    }
}
