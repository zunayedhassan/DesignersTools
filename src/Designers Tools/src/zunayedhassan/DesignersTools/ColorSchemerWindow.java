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
public class ColorSchemerWindow extends Stage {
    public ColorSchemer CurrentColorSchemer = new ColorSchemer(this);
    
    public ColorSchemerWindow() {
        Scene scene = new Scene(this.CurrentColorSchemer, 700, 300);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Color Schemer");
        
        (new CommonTools()).LoadIcon(this, "icons/color-swatch.png");
    }
}
