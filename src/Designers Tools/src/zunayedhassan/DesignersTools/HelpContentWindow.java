/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ZUNAYED_PC
 */
public class HelpContentWindow extends Stage {
    public HelpContent CurrentHelpContent = new HelpContent();
    
    public HelpContentWindow() {
        Scene scene = new Scene(this.CurrentHelpContent, 640, 480);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Help for Designers Tools");
        
        (new CommonTools()).LoadIcon(this, "icons/help-browser.png");
    }
}
