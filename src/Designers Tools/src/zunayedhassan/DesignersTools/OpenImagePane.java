package zunayedhassan.DesignersTools;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Zunayed Hassan
 */
public class OpenImagePane extends HBox {
    public TextField ImagePathTextField = new TextField();
    public Button OpenButton = new Button("Open", new ImageView(new Image(this.getClass().getResourceAsStream("icons/folder-open.png"))));
    public FileChooser OpenImageFileChooser = new FileChooser();
    public File ImageFile = null;
    
    private Stage _stage = null;
    
    public OpenImagePane(BackgroundImagePreviewer previewer, Label originalImageWidth, Label originalImageHeight, Spinner<Integer> imageWidthSpinner, Spinner<Integer> imageHeightSpinner) {
        super(5);
        super.setAlignment(Pos.CENTER_LEFT);
        
        this.ImagePathTextField.setEditable(false);
        
        this.getChildren().addAll(
                this.ImagePathTextField,
                this.OpenButton
        );
        
        this.OpenImageFileChooser.setTitle("Open Image for Background");
        
        this.OpenImageFileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        
        // Events
        this.OpenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ImageFile = OpenImageFileChooser.showOpenDialog(_stage);
                
                if (ImageFile != null) {
                    ImagePathTextField.setText(ImageFile.getAbsolutePath());
                    Image image = new Image("file:" + ImageFile.getAbsolutePath());

                    originalImageWidth.setText(Double.toString(image.getWidth()));
                    originalImageHeight.setText(Double.toString(image.getHeight()));
                    
                    imageWidthSpinner.getValueFactory().setValue((int) image.getWidth());
                    imageHeightSpinner.getValueFactory().setValue((int) image.getHeight());
                    
                    previewer.SetBackgroundPreview(ImageFile.getAbsolutePath(), image.getWidth(), image.getHeight(), "Center", "Center", "Repeat", "Repeat");
                }
            }
        });
    }
    
    public void SetStage(Stage stage) {
        this._stage = stage;
    }
}
