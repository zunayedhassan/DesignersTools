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
public class LinearGradientsWindow extends Stage {
    public LinearGradientDetailsPane CurrentLinearGradientDetailsPane = new LinearGradientDetailsPane(this);
    
    public LinearGradientsWindow() {
        Scene scene = new Scene(this.CurrentLinearGradientDetailsPane, 1300, 400);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Linear Gradient Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/linear-gradient-icon.png");
    }
}
