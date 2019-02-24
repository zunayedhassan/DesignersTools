package zunayedhassan.DesignersTools;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Zunayed Hassan
 */
public class ColorChooserDialog extends Dialog<Color> {
    public ColorChooser ColorsContent = new ColorChooser(true);
    
    public ColorChooserDialog(String title) {
        this.setTitle(title);
        this.SetIcon("icons/color-management.png");
        this.getDialogPane().setContent(this.ColorsContent);
        
        ButtonType applyButtonType = new ButtonType("Apply", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
        
        this.setResultConverter(new Callback<ButtonType, Color>() {
            @Override
            public Color call(ButtonType param) {
                if (param == applyButtonType) {
                    return ColorsContent.ColorProperty.get();
                }
                
                return null;
            }
        });
    }
    
    public void SetIcon(String icon) {
        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(new Image(this.getClass().getResourceAsStream(icon)));
    }
}
