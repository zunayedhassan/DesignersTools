package zunayedhassan.DesignersTools;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ColorSchemer extends BorderPane {
    public ColorChooser ColorChooserPane = new ColorChooser(true);
    public Button ColorPickerButton = new Button("Color Picker", new ImageView(new Image(this.getClass().getResourceAsStream("icons/color-picker.png"))));
    
    private Stage _stage = null;
    
    public ColorSchemer(Stage stage) {
        this._stage = stage;
        
        ((VBox) this.ColorChooserPane.getLeft()).getChildren().add(this.ColorPickerButton);
        this.setCenter(this.ColorChooserPane);
        
        this.ColorPickerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage colorSchemerWindow = GetColorSchemerWindow();
                
                if (colorSchemerWindow != null) {
                    // Minimize current window
                    colorSchemerWindow.hide();
                    
                    // Open a new window
                    Stage screenWindow = new Stage(StageStyle.UNDECORATED);
                    screenWindow.setAlwaysOnTop(true);
                    screenWindow.setTitle("Pick Color from the Screenshot");
                    
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                    double screenWidth = primaryScreenBounds.getWidth();
                    double screenHeight = primaryScreenBounds.getHeight();
                    
                    screenWindow.setWidth(screenWidth);
                    screenWindow.setHeight(screenHeight);
                    screenWindow.setFullScreen(true);
                    screenWindow.setFullScreenExitHint("");
                    
                    try {
                        // Take a screenshot and set it to the Screen Window
                        Thread.sleep(500);
                    }
                    catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                    
                    Image screenshotImage = GetScreenShot();
                    screenWindow.setScene(new Scene(new StackPane(new ImageView(screenshotImage))));
                    
                    // Now show the Screenshow Window
                    screenWindow.show();
                    
                    // Set cursor
                    screenWindow.getScene().setCursor(Cursor.HAND);
                    
                    
                    // Event
                    // If user pressed on [ESCAPE] key
                    screenWindow.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.ESCAPE) {
                                colorSchemerWindow.show();
                                screenWindow.close();
                            }
                        }
                    });
                    
                    // If user choose a color via mouse
                    screenWindow.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Color choosenColor = screenshotImage.getPixelReader().getColor((int) event.getSceneX(), (int) event.getSceneY());
                            SetColor(choosenColor);
                            
                            colorSchemerWindow.show();
                            screenWindow.close();
                        }
                    });
                }
            }
        });
    }
    
    public Stage GetColorSchemerWindow() {
        return this._stage;
    }
    
    public BufferedImage _getBufferdImageAsScreenShot() {
        BufferedImage screencapture = null;
        
        try {
            // Take the Screenshot
            screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        }
        catch (AWTException exception) {
            exception.printStackTrace();
        }
        
        return screencapture;
    }
    
    private WritableImage _getWritableImageFromBufferedImage(BufferedImage bf) {
        WritableImage wr = null;
        
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }
        
        return wr;
    }
    
    public Image GetScreenShot() {
        return this._getWritableImageFromBufferedImage(this._getBufferdImageAsScreenShot());
    }
    
    public void SetColor(Color color) {
        this.ColorChooserPane.ColorChooserBaseControl.SetCustomColor(color);
    }
}
