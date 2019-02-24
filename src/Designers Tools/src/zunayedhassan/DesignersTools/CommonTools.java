package zunayedhassan.DesignersTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Zunayed Hassan
 */
public class CommonTools extends Control {
    public static Stage PRIMARY_STAGE = null;

    public void SetStyleSheet(Scene scene, String styleClass) {
        scene.getStylesheets().add(this.getClass().getResource(styleClass).toExternalForm());
    }

    public Font GetFontFromFile(String fontName, int size) {
        InputStream fontInputStream = this.getClass().getResourceAsStream(fontName);
        Font font = Font.loadFont(fontInputStream, size);

        return font;
    }

    public void LoadIcon(Stage stage, String icon) {
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(icon)));
    }

    public static Scene GET_SCENE_AND_INITIALIZE(Stage stage) {
        PRIMARY_STAGE = stage;

        BaseUI root = new RootUI();
        Scene scene = null;

        if (Settings.IS_SCENE_TRANSPARENT) {
            scene = new Scene(root, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, Color.TRANSPARENT);
        }
        else {
            scene = new Scene(root, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        }

        CommonTools tools = new CommonTools();

        for (String[] fontInformation : Settings.FONTS) {
            tools.GetFontFromFile(fontInformation[0], Integer.parseInt(fontInformation[1].trim()));
        }

        if (Settings.APPLICATION_ICON != null) {
            if (!OSValidator.IS_UNIX()) {
                tools.LoadIcon(PRIMARY_STAGE, Settings.APPLICATION_ICON);
            }
        }

        root.InitializeStyleSheets(scene, Settings.STYLE_CLASSES);
        stage.setTitle(Settings.WINDOW_TITLE);

        return scene;
    }
    
    public static String GET_UNIQUE_ID(String prefix) {
        return (prefix + (new Date()).getTime());
    }
    
    public static String GET_HEX_COLOR_STRING(Color color) {
        return ("#" + color.toString().substring(2, color.toString().length() - 2));
    }
    
    public static void EXPORT_TO_IMAGE(String format, String description, FileChooser fileChooser, Node preview, Stage stage) {
        WritableImage snapshot = preview.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);

        if (CommonTools.PRIMARY_STAGE != null) {
            if (fileChooser.getExtensionFilters().size() > 0) {
                fileChooser.getExtensionFilters().set(0, new FileChooser.ExtensionFilter(description, "*." + format));
            }
            else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, "*." + format));
            }
            
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    ImageIO.write(bufferedImage, format, file);
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    
    public static void OPEN_DOCUMENT(String url) {
        MyApp.GET_INSTANCE().getHostServices().showDocument(url);
    }
    
    public static double GET_DPI() {
        double dpi = Screen.getPrimary().getDpi();
        
        if (dpi < 0) {
            dpi = 96;
        }
        
        return dpi;
    }
}
