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
public class BackgroundImageEditorWindow extends Stage {
    public BackgroundImageEditor CurrentBackgroundImageEditor = new BackgroundImageEditor(this);
    
    public BackgroundImageEditorWindow() {
        Scene scene = new Scene(this.CurrentBackgroundImageEditor, 800, 600);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Background Image Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/image-x-generic.png");
    }
}
