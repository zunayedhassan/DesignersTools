package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ZUnayed Hassan
 */
public class HelpContentWindow extends Stage {
    public HelpContent CurrentHelpContent = new HelpContent();
    
    public HelpContentWindow() {
        Scene scene = new Scene(this.CurrentHelpContent, 650, 480);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Help for Designers Tools");
        
        (new CommonTools()).LoadIcon(this, "icons/help-browser.png");
    }
}
