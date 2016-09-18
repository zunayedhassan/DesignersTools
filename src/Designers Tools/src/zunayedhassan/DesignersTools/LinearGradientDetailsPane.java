/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.String.format;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author Zunayed Hassan
 */
public class LinearGradientDetailsPane extends BorderPane {
    public static final double GRADIENT_PREVIEW_WIDTH = 256d;
    public static final double GRADIENT_PREVIEW_HEIGHT = 25d;
    public static final double GRADIENT_DIRECTION_PANE_SIZE = 64d;
    public static final double GRADIENT_OUTPUT_SIZE = 128;
    
    private static Color _INITIAL_COLOR_1 = Color.BLACK;
    private static Color _INITIAL_COLOR_2 = Color.WHITE;
    
    public Button ReverseButton = new Button();
    public Rectangle PreviewRectangle = new Rectangle(GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT);
    public Pane StopsBasePane = new Pane();
    public Button AddStopButton = new Button("Add Color", new ImageView(new Image(this.getClass().getResourceAsStream("icons/list-add.png"))));
    public Button RemoveStopButton = new Button("Remove Color", new ImageView(new Image(this.getClass().getResourceAsStream("icons/list-remove.png"))));
    public ColorChooser GradientColorChooser = new ColorChooser(true);
    public Rectangle GradientPreview = new Rectangle(GRADIENT_OUTPUT_SIZE, GRADIENT_OUTPUT_SIZE);
    public ObservableList<Stop> Stops = FXCollections.observableArrayList();
    public LinearGradient Gradient = null;
    public Rectangle GradientDirection = new Rectangle(GRADIENT_DIRECTION_PANE_SIZE, GRADIENT_DIRECTION_PANE_SIZE);
    public Line GradientDirectionLine = new Line(0, GRADIENT_DIRECTION_PANE_SIZE / 2d, GRADIENT_DIRECTION_PANE_SIZE, GRADIENT_DIRECTION_PANE_SIZE / 2d);
    public GradientDirectionLineHandle GradientDirectionHandle1 = new GradientDirectionLineHandle(this.GradientDirectionLine.startXProperty(), this.GradientDirectionLine.startYProperty());
    public GradientDirectionLineHandle GradientDirectionHandle2 = new GradientDirectionLineHandle(this.GradientDirectionLine.endXProperty(), this.GradientDirectionLine.endYProperty());
    public LinearGradientDirectionButton LeftToRightLinearGradientDirectionToggleButton = new LinearGradientDirectionButton("icons/go-next.png", 0, 0.5, 1, 0.5, GradientDirectionHandle1, GradientDirectionHandle2);
    public LinearGradientDirectionButton TopToBottomLinearGradientDirectionToggleButton = new LinearGradientDirectionButton("icons/go-down.png", 0.5, 0, 0.5, 1, GradientDirectionHandle1, GradientDirectionHandle2);
    public LinearGradientDirectionButton Diagonal1LinearGradientDirectionToggleButton = new LinearGradientDirectionButton("icons/go-diagonal-1.png", 0, 0, 1, 1, GradientDirectionHandle1, GradientDirectionHandle2);
    public LinearGradientDirectionButton Diagonal2LinearGradientDirectionToggleButton = new LinearGradientDirectionButton("icons/go-diagonal-2.png", 0, 1, 1, 0, GradientDirectionHandle1, GradientDirectionHandle2);
    public Button SaveAsPngImageButton = this.GetExportButton("PNG", "Export to PNG");
    public Button SaveAsJpgImageButton = this.GetExportButton("JPG", "Export to JPG");
    public Button SaveAsSvgButton = this.GetExportButton("SVG", "Export to SVG. Can be opened into\nAdobe Illustrator, Inkscape, \nGIMP");
    public Button SaveAsCssButton = this.GetExportButton("CSS", "Export to CSS");
    
    private Stage _stage = null;
    
    public LinearGradientDetailsPane(Stage stage) {
        this._stage = stage;
        
        BorderPane leftBorderPane = new BorderPane();
        super.setLeft(leftBorderPane);
        
        Label sampleTitle = new Label("Samples");
        sampleTitle.setStyle("-fx-font-weight: bold;");
        
        ToolBar sampleToolbar = new ToolBar(sampleTitle);
        leftBorderPane.setTop(sampleToolbar);
        
        ScrollPane sampleGradientScrollPane = new ScrollPane();
        leftBorderPane.setCenter(sampleGradientScrollPane);
        sampleGradientScrollPane.setFitToWidth(true);
        
        GridPane samplesGridPane = new GridPane();
        sampleGradientScrollPane.setContent(samplesGridPane);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss Default",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.web("#1E5799")),
                                    new Stop(0.50, Color.web("#2989D8")),
                                    new Stop(0.51, Color.web("#207CCA")),
                                    new Stop(1.00, Color.web("#7DB9E8"))
                                } )), 0, 0);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Black Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.web("#4C4C4C")),
                                    new Stop(0.12, Color.web("#595959")),
                                    new Stop(0.25, Color.web("#666666")),
                                    new Stop(0.39, Color.web("#474747")),
                                    new Stop(0.50, Color.web("#2C2C2C")),
                                    new Stop(0.51, Color.web("#000000")),
                                    new Stop(0.60, Color.web("#111111")),
                                    new Stop(0.76, Color.web("#2B2B2B")),
                                    new Stop(0.91, Color.web("#1C1C1C")),
                                    new Stop(1.00, Color.web("#131313"))
                                } )), 1, 0);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D # 16",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(135,224,253,1)),
                                    new Stop(0.40, Color.rgb(83,203,241,1)),
                                    new Stop(1.00, Color.rgb(5,171,224,1))
                                } )), 2, 0);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D # 13",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(240,249,255,1)),
                                    new Stop(0.47, Color.rgb(203,235,255,1)),
                                    new Stop(1.00, Color.rgb(161,219,255,1))
                                } )), 3, 0);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #14",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(122,188,255,1)),
                                    new Stop(0.44, Color.rgb(96,171,248,1)),
                                    new Stop(1.00, Color.rgb(64,150,238,1)),
                                } )), 4, 0);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue to Transparent",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(30,87,153,1)),
                                    new Stop(1.00, Color.rgb(125,185,232,1))
                                } )), 0, 1);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Two Sided Transparent",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(30,87,153,1)),
                                    new Stop(0.62, Color.rgb(89,148,202,1)),
                                    new Stop(0.68, Color.rgb(95,154,207,0.7)),
                                    new Stop(1.00, Color.rgb(125,185,232,0))
                                } )), 1, 1);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Neutral Density",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(0,0,0,0.65)),
                                    new Stop(1.00, Color.rgb(0,0,0,0.00))
                                } )), 2, 1);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White to Transparent",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(1.00, Color.rgb(255,255,255,0))
                                } )), 3, 1);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #15",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(0,183,234,1)),
                                    new Stop(1.00, Color.rgb(0,158,195,1))
                                } )), 4, 1);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #17",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(136,191,232,1)),
                                    new Stop(1.00, Color.rgb(112,176,224,1))
                                } )), 0, 2);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #18",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,255,255,1)),
                                    new Stop(0.35, Color.rgb(221,241,249,1)),
                                    new Stop(1.00, Color.rgb(160,216,239,1))
                                } )), 1, 2);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Flat #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(37,141,200,1)),
                                    new Stop(1.00, Color.rgb(37,141,200,1))
                                } )), 2, 2);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Flat #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(64,150,238,1)),
                                    new Stop(1.00, Color.rgb(64,150,238,1))
                                } )), 3, 2);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(184,225,252,1)),
                                    new Stop(0.10, Color.rgb(169,210,243,1)),
                                    new Stop(0.25, Color.rgb(144,186,228,1)),
                                    new Stop(0.37, Color.rgb(144,188,234,1)),
                                    new Stop(0.50, Color.rgb(144,191,240,1)),
                                    new Stop(0.51, Color.rgb(107,168,229,1)),
                                    new Stop(0.83, Color.rgb(162,218,245,1)),
                                    new Stop(1.00, Color.rgb(189,243,253,1))
                                } )), 4, 2);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(59,103,158,1)),
                                    new Stop(0.50, Color.rgb(43,136,217,1)),
                                    new Stop(0.51, Color.rgb(32,124,202,1)),
                                    new Stop(1.00, Color.rgb(125,185,232,1))
                                } )), 0, 3);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(109,179,242,1)),
                                    new Stop(0.50, Color.rgb(84,163,238,1)),
                                    new Stop(0.51, Color.rgb(54,144,240,1)),
                                    new Stop(1.00, Color.rgb(30,105,222,1))
                                } )), 1, 3);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss #4",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(235,241,246,1)),
                                    new Stop(0.50, Color.rgb(171,211,238,1)),
                                    new Stop(0.51, Color.rgb(137,195,235,1)),
                                    new Stop(1.00, Color.rgb(213,235,251,1))
                                } )), 2, 3);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss #5",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(228,245,252,1)),
                                    new Stop(0.50, Color.rgb(191,232,249,1)),
                                    new Stop(0.51, Color.rgb(159,216,239,1)),
                                    new Stop(1.00, Color.rgb(42,176,237,1))
                                } )), 3, 3);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(206,219,233,1)),
                                    new Stop(0.17, Color.rgb(170,197,222,1)),
                                    new Stop(0.50, Color.rgb(97,153,199,1)),
                                    new Stop(0.51, Color.rgb(58,132,195,1)),
                                    new Stop(0.59, Color.rgb(65,154,214,1)),
                                    new Stop(0.71, Color.rgb(75,184,240,1)),
                                    new Stop(0.84, Color.rgb(58,139,194,1)),
                                    new Stop(1.00, Color.rgb(38,85,139,1))
                                } )), 4, 3);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Grey 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(167,199,220,1)),
                                    new Stop(1.00, Color.rgb(133,178,211,1))
                                } )), 0, 4);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Grey Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(63,76,107,1)),
                                    new Stop(1.00, Color.rgb(63,76,107,1))
                                } )), 1, 4);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Pipe #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(208,228,247,1)),
                                    new Stop(0.24, Color.rgb(115,177,231,1)),
                                    new Stop(0.50, Color.rgb(10,119,213,1)),
                                    new Stop(0.79, Color.rgb(83,159,225,1)),
                                    new Stop(1.00, Color.rgb(135,188,234,1)),
                                } )), 2, 4);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Pipe #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(225,255,255,1)),
                                    new Stop(0.07, Color.rgb(225,255,255,1)),
                                    new Stop(0.12, Color.rgb(225,255,255,1)),
                                    new Stop(0.30, Color.rgb(230,248,253,1)),
                                    new Stop(0.54, Color.rgb(200,238,251,1)),
                                    new Stop(0.75, Color.rgb(190,228,248,1)),
                                    new Stop(1.00, Color.rgb(177,216,245,1))
                                } )), 3, 4);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Pipe",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(179,220,237,1)),
                                    new Stop(0.50, Color.rgb(41,184,229,1)),
                                    new Stop(1.00, Color.rgb(188,224,238,1))
                                } )), 4, 4);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Brown 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(213,206,166,1)),
                                    new Stop(0.40, Color.rgb(201,193,144,1)),
                                    new Stop(1.00, Color.rgb(183,173,112,1))
                                } )), 0, 5);
        
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Brown Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(240,183,161,1)),
                                    new Stop(0.50, Color.rgb(140,51,16,1)),
                                    new Stop(0.51, Color.rgb(117,34,1,1)),
                                    new Stop(1.00, Color.rgb(191,110,78,1))
                                } )), 1, 5);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Brown Red 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(169,3,41,1)),
                                    new Stop(0.44, Color.rgb(143,2,34,1)),
                                    new Stop(1.00, Color.rgb(109,0,25,1))
                                } )), 2, 5);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Gold 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,252,234,1)),
                                    new Stop(1.00, Color.rgb(241,218,54,1)),
                                } )), 3, 5);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(180,221,180,1)),
                                    new Stop(0.17, Color.rgb(131,199,131,1)),
                                    new Stop(0.33, Color.rgb(82,177,82,1)),
                                    new Stop(0.67, Color.rgb(0,138,0,1)),
                                    new Stop(0.83, Color.rgb(0,87,0,1)),
                                    new Stop(1.00, Color.rgb(0,36,0,1)),
                                } )), 4, 5);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(205,235,142,1)),
                                    new Stop(1.00, Color.rgb(165,201,86,1))
                                } )), 0, 6);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(201,222,150,1)),
                                    new Stop(0.44, Color.rgb(138,182,107,1)),
                                    new Stop(1.00, Color.rgb(57,130,53,1))
                                } )), 1, 6);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green 3D #4",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(248,255,232,1)),
                                    new Stop(0.33, Color.rgb(227,245,171,1)),
                                    new Stop(1.00, Color.rgb(183,223,45,1))
                                } )), 2, 6);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(169,219,128,1)),
                                    new Stop(1.00, Color.rgb(150,197,111,1))
                                } )), 3, 6);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(180,227,145,1)),
                                    new Stop(0.50, Color.rgb(97,196,25,1)),
                                    new Stop(1.00, Color.rgb(180,227,145,1))
                                } )), 4, 6);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Flat #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(41,154,11,1)),
                                    new Stop(1.00, Color.rgb(41,154,11,1))
                                } )), 0, 7);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Flat #4",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(107,186,112,1)),
                                    new Stop(1.00, Color.rgb(107,186,112,1))
                                } )), 1, 7);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Flat #5",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(205,235,139,1)),
                                    new Stop(1.00, Color.rgb(205,235,139,1))
                                } )), 2, 7);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Flat #6",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(143,196,0,1)),
                                    new Stop(0.00, Color.rgb(143,196,0,1))
                                } )), 3, 7);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(182,224,38,1)),
                                    new Stop(1.00, Color.rgb(171,220,40,1))
                                } )), 4, 7);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(157,213,58,1)),
                                    new Stop(0.50, Color.rgb(161,213,79,1)),
                                    new Stop(0.51, Color.rgb(128,194,23,1)),
                                    new Stop(1.00, Color.rgb(124,188,10,1))
                                } )), 0, 8);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(230,240,163,1)),
                                    new Stop(0.50, Color.rgb(210,230,56,1)),
                                    new Stop(0.51, Color.rgb(195,216,37,1)),
                                    new Stop(1.00, Color.rgb(219,240,67,1))
                                } )), 1, 8);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(191,210,85,1)),
                                    new Stop(0.50, Color.rgb(142,185,42,1)),
                                    new Stop(0.51, Color.rgb(114,170,0,1)),
                                    new Stop(1.00, Color.rgb(158,203,45,1))
                                } )), 2, 8);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Green Semi Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(180,223,91,1)),
                                    new Stop(1.00, Color.rgb(180,223,91,1))
                                } )), 3, 8);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Gren 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(238,238,238,1)),
                                    new Stop(1.00, Color.rgb(204,204,204,1))
                                } )), 4, 8);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(206,220,231,1)),
                                    new Stop(1.00, Color.rgb(89,106,114,1))
                                } )), 0, 9);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(96,108,136,1)),
                                    new Stop(1.00, Color.rgb(63,76,107,1))
                                } )), 1, 9);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(176,212,227,1)),
                                    new Stop(1.00, Color.rgb(136,186,207,1))
                                } )), 2, 9);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey 3D #4",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(242,245,246,1)),
                                    new Stop(0.37, Color.rgb(227,234,237,1)),
                                    new Stop(1.00, Color.rgb(200,215,220,1))
                                } )), 3, 9);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(216,224,222,1)),
                                    new Stop(0.22, Color.rgb(174,191,188,1)),
                                    new Stop(0.33, Color.rgb(153,175,171,1)),
                                    new Stop(0.50, Color.rgb(142,166,162,1)),
                                    new Stop(0.67, Color.rgb(130,157,152,1)),
                                    new Stop(0.82, Color.rgb(78,92,90,1)),
                                    new Stop(1.00, Color.rgb(14,14,14,1))
                                } )), 4, 9);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Black 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(181,189,200,1)),
                                    new Stop(0.30, Color.rgb(130,140,149,1)),
                                    new Stop(1.00, Color.rgb(40,52,59,1))
                                } )), 0, 10);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Blue 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(184,198,223,1)),
                                    new Stop(1.00, Color.rgb(109,136,183,1))
                                } )), 1, 10);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(238,238,238,1)),
                                    new Stop(1.00, Color.rgb(238,238,238,1))
                                } )), 2, 10);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(226,226,226,1)),
                                    new Stop(0.50, Color.rgb(219,219,219,1)),
                                    new Stop(0.51, Color.rgb(209,209,209,1)),
                                    new Stop(1.00, Color.rgb(254,254,254,1))
                                } )), 3, 10);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(242,246,248,1)),
                                    new Stop(0.50, Color.rgb(216,225,231,1)),
                                    new Stop(0.51, Color.rgb(181,198,208,1)),
                                    new Stop(1.00, Color.rgb(224,239,249,1))
                                } )), 4, 10);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(212,228,239,1)),
                                    new Stop(1.00, Color.rgb(134,174,204,1))
                                } )), 0, 11);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Grey Pipe",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(245,246,246,1)),
                                    new Stop(0.21, Color.rgb(219,220,226,1)),
                                    new Stop(0.49, Color.rgb(184,186,198,1)),
                                    new Stop(0.80, Color.rgb(221,223,227,1)),
                                    new Stop(1.00, Color.rgb(245,246,246,1))
                                } )), 1, 11);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "L Brown 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(243,226,199,1)),
                                    new Stop(0.50, Color.rgb(193,158,103,1)),
                                    new Stop(0.51, Color.rgb(182,141,76,1)),
                                    new Stop(1.00, Color.rgb(233,212,179,1))
                                } )), 2, 11);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "L Green 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(249,252,247,1)),
                                    new Stop(1.00, Color.rgb(245,249,240,1))
                                } )), 3, 11);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Lavender 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(195,217,255,1)),
                                    new Stop(0.41, Color.rgb(177,200,239,1)),
                                    new Stop(1.00, Color.rgb(152,176,217,1))
                                } )), 4, 11);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Neon",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(210,255,82,1)),
                                    new Stop(1.00, Color.rgb(145,232,66,1))
                                } )), 0, 12);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Olive 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,254,253,1)),
                                    new Stop(0.42, Color.rgb(220,227,196,1)),
                                    new Stop(1.00, Color.rgb(174,191,118,1))
                                } )), 1, 12);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Olive 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(228,239,192,1)),
                                    new Stop(1.00, Color.rgb(171,189,115,1))
                                } )), 2, 12);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Olive 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(164,179,87,1)),
                                    new Stop(1.00, Color.rgb(117,137,12,1))
                                } )), 3, 12);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Olive 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(98,125,77,1)),
                                    new Stop(1.00, Color.rgb(31,59,8,1))
                                } )), 4, 12);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Olive Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(115,136,10,1)),
                                    new Stop(1.00, Color.rgb(115,136,10,1))
                                } )), 0, 13);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,175,75,1)),
                                    new Stop(1.00, Color.rgb(255,146,10,1))
                                } )), 1, 13);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(250,198,149,1)),
                                    new Stop(0.47, Color.rgb(245,171,102,1)),
                                    new Stop(1.00, Color.rgb(239,141,49,1))
                                } )), 2, 13);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,197,120,1)),
                                    new Stop(1.00, Color.rgb(251,157,35,1))
                                } )), 3, 13);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange 3D #4",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(249,198,103,1)),
                                    new Stop(1.00, Color.rgb(247,150,33,1))
                                } )), 4, 13);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange 3D #5",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(252,234,187,1)),
                                    new Stop(0.50, Color.rgb(252,205,77,1)),
                                    new Stop(0.51, Color.rgb(248,181,0,1)),
                                    new Stop(1.00, Color.rgb(251,223,147,1))
                                } )), 0, 14);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,168,76,1)),
                                    new Stop(1.00, Color.rgb(255,123,13,1))
                                } )), 1, 14);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange Flat #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,103,15,1)),
                                    new Stop(1.00, Color.rgb(255,103,15,1))
                                } )), 2, 14);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,116,0,1)),
                                    new Stop(1.00, Color.rgb(255,116,0,1))
                                } )), 3, 14);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Orange Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,183,107,1)),
                                    new Stop(0.50, Color.rgb(255,167,61,1)),
                                    new Stop(0.51, Color.rgb(255,124,0,1)),
                                    new Stop(1.00, Color.rgb(255,127,4,1))
                                } )), 4, 14);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,93,177,1)),
                                    new Stop(1.00, Color.rgb(239,1,124,1))
                                } )), 0, 15);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(251,131,250,1)),
                                    new Stop(1.00, Color.rgb(233,60,236,1))
                                } )), 1, 15);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(229,112,231,1)),
                                    new Stop(0.47, Color.rgb(200,94,199,1)),
                                    new Stop(1.00, Color.rgb(168,73,163,1))
                                } )), 2, 15);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(203,96,179,1)),
                                    new Stop(0.50, Color.rgb(173,18,131,1)),
                                    new Stop(1.00, Color.rgb(222,71,172,1))
                                } )), 3, 15);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,0,132,1)),
                                    new Stop(1.00, Color.rgb(255,0,132,1))
                                } )), 4, 15);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(252,236,252,1)),
                                    new Stop(0.50, Color.rgb(251,166,225,1)),
                                    new Stop(0.51, Color.rgb(253,137,215,1)),
                                    new Stop(1.00, Color.rgb(255,124,216,1))
                                } )), 0, 16);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Pink Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(203,96,179,1)),
                                    new Stop(0.50, Color.rgb(193,70,161,1)),
                                    new Stop(0.51, Color.rgb(168,0,119,1)),
                                    new Stop(1.00, Color.rgb(219,54,164,1))
                                } )), 1, 16);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Purple 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(235,233,249,1)),
                                    new Stop(0.50, Color.rgb(216,208,239,1)),
                                    new Stop(0.51, Color.rgb(206,199,236,1)),
                                    new Stop(1.00, Color.rgb(193,191,234,1))
                                } )), 2, 16);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Purple 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(137,137,186,1)),
                                    new Stop(1.00, Color.rgb(137,137,186,1))
                                } )), 3, 16);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,187,187,1)),
                                    new Stop(0.45, Color.rgb(254,144,144,1)),
                                    new Stop(1.00, Color.rgb(255,92,92,1))
                                } )), 4, 16);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(242,130,91,1)),
                                    new Stop(0.50, Color.rgb(229,91,43,1)),
                                    new Stop(1.00, Color.rgb(240,113,70,1))
                                } )), 0, 17);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,48,25,1)),
                                    new Stop(1.00, Color.rgb(207,4,4,1))
                                } )), 1, 17);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red Flat #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,26,0,1)),
                                    new Stop(1.00, Color.rgb(255,26,0,1))
                                } )), 2, 17);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(204,0,0,1)),
                                    new Stop(1.00, Color.rgb(204,0,0,1))
                                } )), 3, 17);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(248,80,50,1)),
                                    new Stop(0.50, Color.rgb(241,111,92,1)),
                                    new Stop(0.51, Color.rgb(246,41,12,1)),
                                    new Stop(0.71, Color.rgb(240,47,23,1)),
                                    new Stop(1.00, Color.rgb(231,56,39,1))
                                } )), 4, 17);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,204,177,1)),
                                    new Stop(0.50, Color.rgb(241,116,50,1)),
                                    new Stop(0.51, Color.rgb(234,85,7,1)),
                                    new Stop(1.00, Color.rgb(251,149,94,1))
                                } )), 0, 18);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red Gloss #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(239,197,202,1)),
                                    new Stop(0.50, Color.rgb(210,75,90,1)),
                                    new Stop(0.51, Color.rgb(186,39,55,1)),
                                    new Stop(0.51, Color.rgb(241,142,153,1))
                                } )), 1, 18);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Red Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(243,197,189,1)),
                                    new Stop(0.50, Color.rgb(232,108,87,1)),
                                    new Stop(0.51, Color.rgb(234,40,3,1)),
                                    new Stop(0.75, Color.rgb(255,102,0,1)),
                                    new Stop(1.00, Color.rgb(199,34,0,1))
                                } )), 2, 18);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Shape 1 Style",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(183,222,237,1)),
                                    new Stop(0.50, Color.rgb(113,206,239,1)),
                                    new Stop(0.51, Color.rgb(33,180,226,1)),
                                    new Stop(1.00, Color.rgb(183,222,237,1))
                                } )), 3, 18);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Shape 2 Style",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(224,243,250,1)),
                                    new Stop(0.50, Color.rgb(216,240,252,1)),
                                    new Stop(0.51, Color.rgb(184,226,246,1)),
                                    new Stop(1.00, Color.rgb(182,223,253,1))
                                } )), 4, 18);
        
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Wax 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,255,232,1)),
                                    new Stop(1.00, Color.rgb(214,219,191,1))
                                } )), 0, 19);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Wax 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(252,255,244,1)),
                                    new Stop(1.00, Color.rgb(233,233,206,1))
                                } )), 1, 19);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Wax 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(252,255,244,1)),
                                    new Stop(0.40, Color.rgb(223,229,215,1)),
                                    new Stop(1.00, Color.rgb(179,190,173,1))
                                } )), 2, 19);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Wax 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(229,230,150,1)),
                                    new Stop(1.00, Color.rgb(209,211,96,1))
                                } )), 3, 19);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Wax Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(234,239,181,1)),
                                    new Stop(1.00, Color.rgb(225,233,160,1))
                                } )), 4, 19);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Black 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(69,72,77,1)),
                                    new Stop(1.00, Color.rgb(0,0,0,1))
                                } )), 0, 20);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Black 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(125,126,125,1)),
                                    new Stop(1.00, Color.rgb(14,14,14,1))
                                } )), 1, 20);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Black Gloss Pipe",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(149,149,149,1)),
                                    new Stop(0.46, Color.rgb(13,13,13,1)),
                                    new Stop(0.50, Color.rgb(1,1,1,1)),
                                    new Stop(0.53, Color.rgb(10,10,10,1)),
                                    new Stop(0.76, Color.rgb(78,78,78,1)),
                                    new Stop(0.87, Color.rgb(56,56,56,1)),
                                    new Stop(1.00, Color.rgb(27,27,27,1))
                                } )), 2, 20);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Black Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(174,188,191,1)),
                                    new Stop(0.50, Color.rgb(110,119,116,1)),
                                    new Stop(0.51, Color.rgb(10,14,10,1)),
                                    new Stop(1.00, Color.rgb(10,8,9,1))
                                } )), 3, 20);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Web 2.0 Blue 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(197,222,234,1)),
                                    new Stop(0.31, Color.rgb(138,187,215,1)),
                                    new Stop(1.00, Color.rgb(6,109,171,1))
                                } )), 4, 20);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(247,251,252,1)),
                                    new Stop(0.40, Color.rgb(217,237,242,1)),
                                    new Stop(1.00, Color.rgb(173,217,228,1))
                                } )), 0, 21);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(214,249,255,1)),
                                    new Stop(0.00, Color.rgb(158,232,250,1))
                                } )), 1, 21);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #3",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(233,246,253,1)),
                                    new Stop(1.00, Color.rgb(211,238,251,1))
                                } )), 2, 21);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #4",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(99,182,219,1)),
                                    new Stop(1.00, Color.rgb(48,157,207,1))
                                } )), 3, 21);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(44,83,158,1)),
                                    new Stop(1.00, Color.rgb(44,83,158,1))
                                } )), 4, 21);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Ble 3D #5",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(169,228,247,1)),
                                    new Stop(1.00, Color.rgb(15,180,231,1))
                                } )), 0, 22);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #5",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(147,206,222,1)),
                                    new Stop(0.41, Color.rgb(117,189,209,1)),
                                    new Stop(1.00, Color.rgb(73,165,191,1))
                                } )), 1, 22);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #6",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(178,225,255,1)),
                                    new Stop(1.00, Color.rgb(102,182,252,1))
                                } )), 2, 22);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #9",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(79,133,187,1)),
                                    new Stop(1.00, Color.rgb(79,133,187,1))
                                } )), 3, 22);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #10",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(222,239,255,1)),
                                    new Stop(1.00, Color.rgb(152,190,222,1))
                                } )), 4, 22);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #11",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(73,192,240,1)),
                                    new Stop(1.00, Color.rgb(44,175,227,1))
                                } )), 0, 23);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #10",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(222,239,255,1)),
                                    new Stop(1.00, Color.rgb(152,190,222,1))
                                } )), 1, 23);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3D #12",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,255,255,1)),
                                    new Stop(1.00, Color.rgb(210,235,249,1))
                                } )), 2, 23);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3d #8",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(167,207,223,1)),
                                    new Stop(1.00, Color.rgb(35,83,138,1))
                                } )), 3, 23);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue 3d #7",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(73,155,234,1)),
                                    new Stop(1.00, Color.rgb(32,124,229,1))
                                } )), 4, 23);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Blue Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(53,106,160,1)),
                                    new Stop(1.00, Color.rgb(53,106,160,1))
                                } )), 0, 24);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(0.47, Color.rgb(246,246,246,1)),
                                    new Stop(1.00, Color.rgb(237,237,237,1))
                                } )), 1, 24);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(242,249,254,1)),
                                    new Stop(1.00, Color.rgb(214,240,253,1))
                                } )), 2, 24);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(1.00, Color.rgb(229,229,229,1))
                                } )), 3, 24);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(0.50, Color.rgb(241,241,241,1)),
                                    new Stop(0.51, Color.rgb(225,225,225,1)),
                                    new Stop(1.00, Color.rgb(246,246,246,1))
                                } )), 4, 24);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(0.50, Color.rgb(243,243,243,1)),
                                    new Stop(0.51, Color.rgb(237,237,237,1)),
                                    new Stop(1.00, Color.rgb(255,255,255,1))
                                } )), 0, 25);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(246,248,249,1)),
                                    new Stop(0.50, Color.rgb(229,235,238,1)),
                                    new Stop(0.51, Color.rgb(215,222,227,1)),
                                    new Stop(1.00, Color.rgb(245,247,249,1))
                                } )), 1, 25);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White Gloss #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(0.50, Color.rgb(241,241,241,1)),
                                    new Stop(0.51, Color.rgb(225,225,225,1)),
                                    new Stop(1.00, Color.rgb(246,246,246,1))
                                } )), 2, 25);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White Gloss #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,255,1)),
                                    new Stop(0.50, Color.rgb(243,243,243,1)),
                                    new Stop(0.51, Color.rgb(237,237,237,1)),
                                    new Stop(1.00, Color.rgb(255,255,255,1))
                                } )), 3, 25);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "White Gloss",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(246,248,249,1)),
                                    new Stop(0.50, Color.rgb(229,235,238,1)),
                                    new Stop(0.51, Color.rgb(215,222,227,1)),
                                    new Stop(1.00, Color.rgb(245,247,249,1))
                                } )), 4, 25);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Yellow 3D #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(246,230,180,1)),
                                    new Stop(1.00, Color.rgb(237,144,23,1))
                                } )), 0, 26);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Yellow 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(234,185,45,1)),
                                    new Stop(1.00, Color.rgb(199,152,16,1))
                                } )), 1, 26);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Yellow 3D #2",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,214,94,1)),
                                    new Stop(1.00, Color.rgb(254,191,4,1))
                                } )), 2, 26);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Yellow 3D",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(241,231,103,1)),
                                    new Stop(1.00, Color.rgb(254,182,69,1))
                                } )), 3, 26);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Yellow Flat #1",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(255,255,136,1)),
                                    new Stop(1.00, Color.rgb(255,255,136,1))
                                } )), 4, 26);
        
        samplesGridPane.add(
                new SampleLinearGradientButton(
                        "Yellow Flat",
                        new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                                new Stop[] {
                                    new Stop(0.00, Color.rgb(254,191,1,1)),
                                    new Stop(1.00, Color.rgb(254,191,1,1))
                                } )), 0, 27);
        
        
        
        SplitPane baseSplitPane = new SplitPane();
        baseSplitPane.setDividerPositions(0.75, 0.25);
        
        super.setCenter(baseSplitPane);
        
        BorderPane gradientEditorPane = new BorderPane();
        
        BorderPane topPane = new BorderPane();
        ToolBar topToolBar = new ToolBar(topPane);
        topToolBar.setPadding(new Insets(15));
        gradientEditorPane.setTop(topToolBar);
        
        Pane gradientDirectionPane = new Pane(this.GradientDirection);
        this.GradientDirection.setTranslateX(1);
        this.GradientDirection.setTranslateY(1);
        gradientDirectionPane.setPrefSize(GRADIENT_DIRECTION_PANE_SIZE + 2d, GRADIENT_DIRECTION_PANE_SIZE + 2d);
        gradientDirectionPane.setBorder(new Border(new BorderStroke(Color.web("#B5B5B5"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        
        this.GradientDirection.setStroke(Color.web("#FAFAFA"));
        this.GradientDirection.setStrokeWidth(1);
        this.GradientDirection.setStyle("-fx-fill: -fx-base;");
        
        this.GradientDirectionLine.setStyle(
                "-fx-stroke: -fx-dark-text-color;" +
                "-fx-stroke-width: 0.5;"
        );
        
        gradientDirectionPane.getChildren().addAll(
                this.GradientDirectionLine,
                this.GradientDirectionHandle1,
                this.GradientDirectionHandle2
        );
        
        HBox gradientDirectionBase = new HBox(15, new Label("Direction"), gradientDirectionPane);
        gradientDirectionBase.setAlignment(Pos.CENTER_LEFT);
        
        GridPane resetDirectionPane = new GridPane();
        resetDirectionPane.setPadding(new Insets(0, 0, 0, 15));
        resetDirectionPane.add(this.LeftToRightLinearGradientDirectionToggleButton, 0, 0);
        resetDirectionPane.add(this.TopToBottomLinearGradientDirectionToggleButton, 1, 0);
        resetDirectionPane.add(this.Diagonal1LinearGradientDirectionToggleButton, 0, 1);
        resetDirectionPane.add(this.Diagonal2LinearGradientDirectionToggleButton, 1, 1);
        
        topToolBar.getItems().addAll(
                new Separator(),
                gradientDirectionBase,
                resetDirectionPane
        );
        
        this.ReverseButton.setText("Reverse");
        this.ReverseButton.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("icons/document-revert.png"))));
        this.ReverseButton.setTooltip(new Tooltip("Revert Colors"));
        this.ReverseButton.setContentDisplay(ContentDisplay.TOP);
        
        topPane.setLeft(this.ReverseButton);
        
        VBox gradientPreviewPane = new VBox(5);
        gradientPreviewPane.setPadding(new Insets(0, 15, 0, 15));

        this.PreviewRectangle.setStroke(LinearGradientStop.MOUSE_EXIT_COLOR);
        
        gradientPreviewPane.getChildren().addAll(
                this.PreviewRectangle,
                this.StopsBasePane
        );
        
        topPane.setCenter(gradientPreviewPane);
        
        this.StopsBasePane.setMinSize(GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT / 4);
        this.StopsBasePane.setMaxSize(GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT / 4);
        
        this.StopsBasePane.setMinSize(GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT / 4);
        this.StopsBasePane.setMaxSize(GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT / 4);
        
        VBox stopsEditPane = new VBox(5);
        
        topPane.setRight(stopsEditPane);
        
        baseSplitPane.getItems().add(gradientEditorPane);
        
        final double BUTTON_SIZE = 128;
        
        this.AddStopButton.setMinWidth(BUTTON_SIZE);
        this.AddStopButton.setMaxWidth(BUTTON_SIZE);
        this.RemoveStopButton.setMinWidth(BUTTON_SIZE);
        this.RemoveStopButton.setMaxWidth(BUTTON_SIZE);
        
        this.AddStopButton.setTooltip(new Tooltip("Add Color"));
        this.RemoveStopButton.setTooltip(new Tooltip("Remove Color"));
        
        stopsEditPane.getChildren().addAll(
                this.AddStopButton,
                this.RemoveStopButton
        );
        
        this.GradientPreview.setStroke(Color.BLACK);
        this.GradientPreview.setStrokeWidth(1);
        
        
        Label previewTitle = new Label("Preview");
        previewTitle.setStyle("-fx-font-weight: bold;");
        ToolBar previewGradientToolBar = new ToolBar(previewTitle);
        
        BorderPane previewBorderPane = new BorderPane();
        previewBorderPane.setTop(previewGradientToolBar);
        
        StackPane previewStackPane = new StackPane(this.GradientPreview);
        previewStackPane.setPadding(new Insets(10));
        previewBorderPane.setCenter(previewStackPane);
        baseSplitPane.getItems().add(previewBorderPane);
        
        ToolBar exportToolBar = new ToolBar();
        previewBorderPane.setBottom(exportToolBar);
        
        exportToolBar.getItems().addAll(
                new Label("Export"),
                this.SaveAsPngImageButton,
                this.SaveAsJpgImageButton,
                this.SaveAsSvgButton,
                this.SaveAsCssButton
        );
        
        
        this.AddNewGradientStop(_INITIAL_COLOR_1, 0);
        this.AddNewGradientStop(_INITIAL_COLOR_2, 1);
        
        gradientEditorPane.setCenter(this.GradientColorChooser);
        
        ((LinearGradientStop) this.StopsBasePane.getChildren().get(1)).SetSelect();
        
        // Events
        this.AddStopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddNewGradientStop(Color.CORNFLOWERBLUE, 0.5);
                DeselectAllGradientStops();
                ((LinearGradientStop) StopsBasePane.getChildren().get(StopsBasePane.getChildren().size() - 1)).SetSelect();
            }
        });
        
        this.RemoveStopButton.setOnAction(new EventHandler<ActionEvent>() {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You need to select a color to remove it", ButtonType.OK);
            
            @Override
            public void handle(ActionEvent event) {
                if (Stops.size() > 2) {
                    boolean isAnyGradientColorSelected = false;
                
                    for (Node node : StopsBasePane.getChildren()) {
                        if (node instanceof LinearGradientStop) {
                            LinearGradientStop currentStop = (LinearGradientStop) node;

                            if (currentStop.IsSelected()) {
                                isAnyGradientColorSelected = true;

                                for (int i = 0; i < Stops.size(); i++) {
                                    if (Stops.get(i).equals(currentStop.GetStop())) {
                                        Stops.remove(i);
                                        break;
                                    }
                                }

                                StopsBasePane.getChildren().remove(currentStop);

                                break;
                            }
                        }
                    }

                    if (isAnyGradientColorSelected) {
                        UpdateGradient();
                        
                        DeselectAllGradientStops();
                        ((LinearGradientStop) StopsBasePane.getChildren().get(StopsBasePane.getChildren().size() - 1)).SetSelect();
                    }
                    else {
                        alert.showAndWait();
                    }
                }
            }
        });
        
        this.ReverseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StopsBasePane.getChildren().clear();
                
                ArrayList<Double> offests = new ArrayList<>();
                ArrayList<Color> colors = new ArrayList<>();
                
                ArrayList<Stop> sortedStops = new ArrayList<>();
                
                for (Stop stop : Stops) {
                    sortedStops.add(stop);
                }
                
                for (int i = 0; i < Stops.size(); i++) {
                    for (int j = 0; j < Stops.size(); j++) {
                        if (sortedStops.get(i).getOffset() > sortedStops.get(j).getOffset()) {
                            Stop temp = sortedStops.get(i);
                            
                            sortedStops.set(i, sortedStops.get(j));
                            sortedStops.set(j, temp);
                        }
                    }
                }
                
                Stops.clear();
                
                for (Stop stop : sortedStops) {
                    offests.add(stop.getOffset());
                    colors.add(stop.getColor());
                }
                
                for (int i = 0,
                         j = colors.size() - 1;
                        
                     i <= colors.size() - 1;
                
                     i++,
                     j--) {
                
                    AddNewGradientStop(colors.get(j), offests.get(i));
                }
                
                ((LinearGradientStop) StopsBasePane.getChildren().get(0)).SetSelect();
            }
        });
        
        this.GradientColorChooser.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color choosenColor) {
                for (Node node : StopsBasePane.getChildren()) {
                    if (node instanceof LinearGradientStop) {
                        LinearGradientStop currentGradientStopButton = (LinearGradientStop) node;
                        
                        if (currentGradientStopButton.IsSelected()) {
                            currentGradientStopButton.SetColor(choosenColor);
                            
                            break;
                        }
                    }
                }
            }
        });
        
        this.GradientDirectionLine.startXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number value) {
                UpdateGradient();
            }
        });
        
        this.GradientDirectionLine.startYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number value) {
                UpdateGradient();
            }
        });
        
        this.GradientDirectionLine.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number value) {
                UpdateGradient();
            }
        });
        
        this.GradientDirectionLine.endYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number value) {
                UpdateGradient();
            }
        });
        
        this.SaveAsPngImageButton.setOnAction(new EventHandler<ActionEvent>() {
            FileChooser fileChooser = new FileChooser();
            
            @Override
            public void handle(ActionEvent event) {
                ExportToImage("png", "Portable Network Graphics", fileChooser);
            }
        });
        
        this.SaveAsJpgImageButton.setOnAction(new EventHandler<ActionEvent>() {
            FileChooser fileChooser = new FileChooser();
            
            @Override
            public void handle(ActionEvent event) {
                ExportToImage("jpg", "Joint Photographic Experts Group (Jpeg)", fileChooser);
            }
        });
        
        this.SaveAsSvgButton.setOnAction(new EventHandler<ActionEvent>() {
            FileChooser fileChooser = new FileChooser();
            
            @Override
            public void handle(ActionEvent event) {
                WriteToSvgFromLinearGradient(fileChooser);
            }
        });
        
        this.SaveAsCssButton.setOnAction(new EventHandler<ActionEvent>() {
            FileChooser fileChooser = new FileChooser();
            
            @Override
            public void handle(ActionEvent event) {
                ExportToCss(fileChooser);
            }
        });
        
        for (Node node : samplesGridPane.getChildren()) {
            SampleLinearGradientButton gradientButton = (SampleLinearGradientButton) node;
            
            gradientButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ClearPreviewGradient();
                    
                    LinearGradient gradient = gradientButton.SampleGradient;
                    
                    for (Stop stop : gradient.getStops()) {
                        AddNewGradientStop(stop.getColor(), stop.getOffset());
                    }
                    
                    GradientDirectionHandle1.setCenterX(GRADIENT_DIRECTION_PANE_SIZE * gradient.getStartX());
                    GradientDirectionHandle1.setCenterY(GRADIENT_DIRECTION_PANE_SIZE * gradient.getStartY());
                    GradientDirectionHandle2.setCenterX(GRADIENT_DIRECTION_PANE_SIZE * gradient.getEndX());
                    GradientDirectionHandle2.setCenterY(GRADIENT_DIRECTION_PANE_SIZE * gradient.getEndY());
                    
                    ((LinearGradientStop) StopsBasePane.getChildren().get(0)).SetSelect();
                }
            });
        }
    }
    
    public void ClearPreviewGradient() {
        PreviewRectangle.setFill(null);
        GradientPreview.setFill(null);
        StopsBasePane.getChildren().clear();
        Stops.clear();
    }
    
    public void AddNewGradientStop(Color color, double position) {        
        this.StopsBasePane
                .getChildren()
                .add(
                        new LinearGradientStop(
                                color,
                                position,
                                GRADIENT_PREVIEW_WIDTH,
                                this.StopsBasePane,
                                this.Stops,
                                this.Gradient,
                                this.PreviewRectangle,
                                this.GradientColorChooser,
                                this.GradientPreview,
                                this.GradientDirectionLine
                        )
                );
    }
    
    public void UpdateGradient() {
        this.Gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, this.Stops);
        this.PreviewRectangle.setFill(this.Gradient);
        
        double x1 = this.GetGradientDirection(this.GradientDirectionLine.getStartX());
        double y1 = this.GetGradientDirection(this.GradientDirectionLine.getStartY());
        double x2 = this.GetGradientDirection(this.GradientDirectionLine.getEndX());
        double y2 = this.GetGradientDirection(this.GradientDirectionLine.getEndY());
        
        LinearGradient mainGradient = new LinearGradient(x1, y1, x2, y2, true, CycleMethod.NO_CYCLE, this.Stops);
        this.GradientPreview.setFill(mainGradient);
    }
    
    public void DeselectAllGradientStops() {
        for (Node node : StopsBasePane.getChildren()) {
            if (node instanceof LinearGradientStop) {
                ((LinearGradientStop) node).SetDeselect();
            }
        }
    }
    
    public double GetGradientDirection(double value) {
        return ((100.0 / GRADIENT_DIRECTION_PANE_SIZE) * value) / 100.0;
    }
    
    public Button GetExportButton(String text, String tooltip) {
        Button button = new Button(text);
        button.setTooltip(new Tooltip(tooltip));
        
        return button;
    }
    
    public void ExportToImage(String format, String description, FileChooser fileChooser) {
        WritableImage snapshot = this.GradientPreview.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);

        if (this._stage != null) {
            if (fileChooser.getExtensionFilters().size() > 0) {
                fileChooser.getExtensionFilters().set(0, new FileChooser.ExtensionFilter(description, "*." + format));
            }
            else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, "*." + format));
            }
            
            File file = fileChooser.showSaveDialog(this._stage);

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
    
    public void ExportToText(String format, String description, FileChooser fileChooser, String text) {
        if (this._stage != null) {
            if (fileChooser.getExtensionFilters().size() > 0) {
                fileChooser.getExtensionFilters().set(0, new FileChooser.ExtensionFilter(description, "*." + format));
            }
            else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, "*." + format));
            }

            File file = fileChooser.showSaveDialog(this._stage);

            if (file != null) {
                PrintWriter out = null;

                try {
                    out = new PrintWriter(file);
                    out.print(text);
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
                finally {
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }
    }
    
    public void ExportToCss(FileChooser fileChooser) {
        double startX = GradientDirectionLine.getStartX();
        double startY = GradientDirectionLine.getStartY();
        double endX = GradientDirectionLine.getEndX();
        double endY = GradientDirectionLine.getEndY();

        double midX = (startX + endX) / 2.0;
        double midY = (startY + endY) / 2.0;

        double deltaX = startX - midX;
        double deltaY = startY - midY;

        double coefficient = 90.0;

        if (deltaX > 0) {
            coefficient = 270.0;
        }

        double angle = Math.atan(deltaY / deltaX) * (180.0 / Math.PI) + coefficient;

        DecimalFormat formatter = new DecimalFormat("#.##");
        String angleAsString = formatter.format(angle);

        String css = "background: linear-gradient(" + angleAsString + "deg";

        for (Stop stop : Stops) {
            String colotHexString = GetHexColorString(stop.getColor());
            String offset = Double.toString(stop.getOffset() * 100.0);

            css += ", " + colotHexString + " " + offset + "%";
        }

        css += ");";

        css = GetUniqueId(".linearGradient") + " {\n\t" + css + "\n}";

        ExportToText("css", "Cascading Style Sheets", fileChooser, css);
    }
    
    public void WriteToSvgFromLinearGradient(FileChooser fileChooser) {
        LinearGradient outputGradient = (LinearGradient) GradientPreview.getFill();
        String format = "svg";
        String description = "Scalable Vector Graphics";
        String svg = GetSvgFromLinearGradient(outputGradient);

        ExportToText(format, description, fileChooser, svg);
    }
    
    public String GetHexColorString(Color color) {
        return ("#" + color.toString().substring(2, color.toString().length() - 2));
    }
    
    public String GetUniqueId(String prefix) {
        return (prefix + (new Date()).getTime());
    }
    
    public String GetSvgFromLinearGradient(LinearGradient outputGradient) {
        String gradientId = GetUniqueId("linearGradient");
        String objectId = GetUniqueId("obj");

        String svgContent = "<svg width=\"" + GRADIENT_OUTPUT_SIZE + "\" height=\"" + GRADIENT_OUTPUT_SIZE + "\" xmlns=\"http://www.w3.org/2000/svg\"><defs><linearGradient y2=\"" + outputGradient.getEndY() + "\" x2=\"" + outputGradient.getEndX() + "\" y1=\"" + outputGradient.getStartY() + "\" x1=\"" + outputGradient.getStartX() + "\" id=\"" + gradientId + "\">";

        for (Stop stop : outputGradient.getStops()) {
            svgContent += "<stop offset=\"" + stop.getOffset() + "\" stop-opacity=\"1.0\" stop-color=\"" + GetHexColorString(stop.getColor()) + "\" />";
        }

        svgContent += "</linearGradient></defs><g><rect id=\"" + objectId + "\" height=\"" + GRADIENT_OUTPUT_SIZE + "\" width=\"" + GRADIENT_OUTPUT_SIZE + "\" y=\"0\" x=\"0\" fill=\"url(#" + gradientId + ")\"/></g></svg>";

        return svgContent;
    }
}
