package zunayedhassan.DesignersTools;

import java.io.File;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Zunayed Hassan
 */
public class SampleBackgroundImageListElement extends BorderPane {
    public static final double SIZE_HEIGHT = 160;
    
    public Pane PreviewPane = new Pane();
    public Pane Preview = null;
    
    public SampleBackgroundImageListElement(
            String title,
            String image,
            Pane preview,
            OpenImagePane openImagePane,
            Label originalImageWidth,
            Label originalImageHeight,
            Spinner<Integer> imageWidthSpinner,
            Spinner<Integer> imageHeightSpinner) {
        
        this.Preview = preview;
        
        Image bgImage = new Image("file:" + image);
        
        Background background = new Background(
                new BackgroundImage(
                        new Image("file:" + image), 
                        BackgroundRepeat.REPEAT, 
                        BackgroundRepeat.REPEAT, 
                        new BackgroundPosition(Side.LEFT, 0.5, true, Side.TOP, 0.5, true),      // Center
                        new BackgroundSize(bgImage.getWidth(), bgImage.getHeight(), false, false, false, false)
                )
        );
        
        this.PreviewPane.setBackground(background);
        this.PreviewPane.setStyle("-fx-min-height: " + SIZE_HEIGHT + "px; -fx-pref-height: " + SIZE_HEIGHT + "px;");
        
        this.setCenter(this.PreviewPane);
        this.setBottom(new Label(title));
        
        this.setCursor(Cursor.HAND);
        
        // Events
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Background background = PreviewPane.getBackground();
                
                Image bgImage = background.getImages().get(0).getImage();
                BackgroundRepeat repeatX = BackgroundRepeat.REPEAT;
                BackgroundRepeat repeatY = BackgroundRepeat.REPEAT;
                BackgroundPosition backgroundPosition = BackgroundPosition.CENTER;
                BackgroundSize backgroundSize = new BackgroundSize(bgImage.getWidth(), bgImage.getHeight(), false, false, false, false);
                
                Background outputBackground = new Background(
                        new BackgroundImage(bgImage, repeatX, repeatY, backgroundPosition, backgroundSize)
                );
                
                String imagePath = System.getProperty("user.dir") + image;
                openImagePane.ImageFile = new File("file:" + image);
                
                openImagePane.ImagePathTextField.setText(imagePath);
                originalImageWidth.setText(Double.toString(bgImage.getWidth()));
                originalImageHeight.setText(Double.toString(bgImage.getHeight()));
                imageWidthSpinner.getValueFactory().setValue((int) bgImage.getWidth());
                imageHeightSpinner.getValueFactory().setValue((int) bgImage.getHeight());
                
                Preview.setBackground(outputBackground);
            }
        });
    }
}
