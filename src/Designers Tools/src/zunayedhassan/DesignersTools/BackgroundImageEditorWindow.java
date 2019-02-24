package zunayedhassan.DesignersTools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Zunayed Hassan
 */
public class BackgroundImageEditorWindow extends Stage {
    public BackgroundImageEditor CurrentBackgroundImageEditor = new BackgroundImageEditor(this);
    
    public BackgroundImageEditorWindow() {
        Scene scene = new Scene(this.CurrentBackgroundImageEditor, 850, 650);
        this.initStyle(StageStyle.DECORATED);
        this.setScene(scene);
        this.setTitle("Background Image Editor");
        
        (new CommonTools()).LoadIcon(this, "icons/image-x-generic.png");
    }
}
