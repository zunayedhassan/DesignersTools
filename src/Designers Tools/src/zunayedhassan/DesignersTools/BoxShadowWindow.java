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
class BoxShadowWindow extends Stage {
    public BoxShadow CurrentBoxShadow = new BoxShadow(this);
    
    public BoxShadowWindow() {
        Scene scene = new Scene(this.CurrentBoxShadow, 650, 300);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Box Shaow Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/image-shadow.png");
    }
}
