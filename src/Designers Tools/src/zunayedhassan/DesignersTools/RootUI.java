package zunayedhassan.DesignersTools;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Zunayed Hassan
 */
public class RootUI extends BaseUI {
    public ScreenRulerWindow ScreenRulerProgram = new ScreenRulerWindow();
    public ColorSchemerWindow ColorSchemerProgram = new ColorSchemerWindow();
    public BoxShadowWindow BoxShadowProgram = new BoxShadowWindow();
    public LinearGradientsWindow LinearGradientProgram = new LinearGradientsWindow();
    public RadialGradientWindow RadialGradientProgram = new RadialGradientWindow();
    public BackgroundImageEditorWindow BackgroundImageEditorProgram = new BackgroundImageEditorWindow();
    public Button ScreenRulerButton = this.GetToolButton("Screen Ruler", "icons/ruler-y.png", this.ScreenRulerProgram);
    public Button ColorSwatchButton = this.GetToolButton("Color Chooser", "icons/color-swatch.png", this.ColorSchemerProgram);
    public Button LinearGradientButton = this.GetToolButton("Linear Gradient", "icons/linear-gradient-icon.png", this.LinearGradientProgram);
    public Button RadialGradientButton = this.GetToolButton("Radial Gradient", "icons/radial-gradient-icon.png", this.RadialGradientProgram);
    public Button BackgroundImageButton = this.GetToolButton("Background", "icons/image-x-generic.png", this.BackgroundImageEditorProgram);
    public Button BoxShadowButton = this.GetToolButton("Box Shadow", "icons/image-shadow.png", this.BoxShadowProgram);
    public Button ScreenshotButton = this.GetToolButton("Take Screenshot", "icons/video-display.png");
    public Button HelpButton = new Button("Help", new ImageView(new Image(this.getClass().getResourceAsStream("icons/help-browser.png"))));
    public Button AboutButton = new Button("About", new ImageView(new Image(this.getClass().getResourceAsStream("icons/help-about.png"))));
    public FileChooser ScreenCaptureFileChooser = new FileChooser();
    public AboutDialog ProgramAboutDialog = new AboutDialog();
    public HelpContentWindow HelpProgram = new HelpContentWindow();
    
    public RootUI() {
        CommonTools.PRIMARY_STAGE.initStyle(StageStyle.DECORATED);
        CommonTools.PRIMARY_STAGE.setResizable(false);
        
        ToolBar mainPane = new ToolBar();
        HBox helpHPane = new HBox(5);
        BorderPane helpPane = new BorderPane();
        
        this.setTop(mainPane);
        this.setCenter(helpPane);
        
        GridPane mainPaneBase = new GridPane();
        mainPaneBase.setHgap(10);
        mainPaneBase.setVgap(10);
        mainPaneBase.setPadding(new Insets(16, 0, 16, 16));
        mainPane.getItems().add(mainPaneBase);
        
        mainPaneBase.add(this.ScreenRulerButton, 0, 0);
        mainPaneBase.add(this.ColorSwatchButton, 1, 0);
        mainPaneBase.add(this.BoxShadowButton, 2, 0);
        mainPaneBase.add(this.ScreenshotButton, 3, 0);
        mainPaneBase.add(this.LinearGradientButton, 0, 1);
        mainPaneBase.add(this.RadialGradientButton, 1, 1);
        mainPaneBase.add(this.BackgroundImageButton, 2, 1);
        
        
        helpPane.setRight(helpHPane);
        helpHPane.setPadding(new Insets(16));
        
        helpHPane.getChildren().addAll(
                this.HelpButton,
                this.AboutButton
        );
        
        this.ScreenCaptureFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Portable Network Graphics", "*.png"));
        this.ScreenCaptureFileChooser.setTitle("Save snapshot to");
        
        this.ScreenshotButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetMinimizeCurrentWindow(true);
                TakeScreenShot();
            }
        });
        
        this.AboutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProgramAboutDialog.showAndWait();
            }
        });
        
        this.HelpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HelpProgram.show();
            }
        });
    }
    
    public Button GetToolButton(String title, String icon, Stage window) {
        Button button = this.GetToolButton(title, icon);
        
        // Events
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetMinimizeCurrentWindow(true);
                window.show(); 
            }
        });
        
        return button;
    }
    
    public Button GetToolButton(String title, String icon) {
        Button button = new Button();
        
        VBox layout = new VBox(5);
        layout.setAlignment(Pos.CENTER);
        
        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size: 14px;");
        
        layout.getChildren().addAll(
                new ImageView(new Image(this.getClass().getResourceAsStream(icon))),
                titleText
        );
        
        final double BUTTON_SIZE = 120;
        
        button.setGraphic(layout);
        button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        button.setPrefSize(button.getMinWidth(), button.getMinHeight());
        button.setCursor(Cursor.HAND);
        
        return button;
    }
    
    public void SetMinimizeCurrentWindow(boolean isMinimize) {
        CommonTools.PRIMARY_STAGE.setIconified(isMinimize);
    }
    
    public void TakeScreenShot() {
        try {
            Thread.sleep(500);
            
            // Take the Screenshot
            BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            
            File file = this.ScreenCaptureFileChooser.showSaveDialog(CommonTools.PRIMARY_STAGE);
            
            if (file != null) {
                ImageIO.write(screencapture, "png", file);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        catch (AWTException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
