/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        Scene scene = new Scene(this.CurrentRadialGradientDetailsPane, 1300, 400);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Radial Gradient Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/radial-gradient-icon.png");
    }
}
