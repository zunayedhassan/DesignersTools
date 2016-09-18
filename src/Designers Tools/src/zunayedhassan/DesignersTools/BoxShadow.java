/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.SegmentedButton;

public class BoxShadow extends BorderPane {
    public static final double SEPERATOR_HEIGHT = 10;
    public static final double LABEL_SIZE = 100;
    
    protected VBox leftPane = new VBox(5);
    protected BorderPane centerPane = new BorderPane();
    
    public ToggleButton OutlineToggleButton = new ToggleButton("Outline");
    public ToggleButton InsetToggleButton = new ToggleButton("Inset");
    public SegmentedButton BoxShadowTypeToggleGroup = new SegmentedButton(this.OutlineToggleButton, this.InsetToggleButton);
    
    public DropShadow PreviewDropShadow = new DropShadow();
    public InnerShadow PreviewInnerShadow = new InnerShadow();
    
    public DoubleProperty HorizontalLengthProperty = new SimpleDoubleProperty(1);
    public DoubleProperty VerticalLengthProperty = new SimpleDoubleProperty(1);
    public DoubleProperty BlurRadiusProperty = new SimpleDoubleProperty(10);
    public DoubleProperty SpreadRadiusProperty = new SimpleDoubleProperty(1);
    
    public PropertyElement HorizontalLength = new PropertyElement("Horizontal Length", this.HorizontalLengthProperty, -100, 100, 1);
    public PropertyElement VerticalLength = new PropertyElement("Vertical Length", this.VerticalLengthProperty, -100, 100, 1);
    public PropertyElement BlurRadius = new PropertyElement("Blur Radius", this.BlurRadiusProperty, 0, 127, 10);
    public PropertyElement SpreadRadius = new PropertyElement("Spread Radius", this.SpreadRadiusProperty, 0, 1, 0);
    public ColorPicker ShadowColor = new ColorPicker(Color.DARKGRAY);
    public ColorPicker BoxColor = new ColorPicker(Color.ORANGE);
    public Rectangle PreviewRectangle = new Rectangle(128, 128, this.BoxColor.getValue());
    public Button PngButton = new Button("PNG");
    public Button CssButton = new Button("CSS");
    
    public FileChooser CssFileChooser = new FileChooser();
    public FileChooser JpgFileChooser = new FileChooser();
    public FileChooser PngFileChooser = new FileChooser();
    
    protected StackPane previewPane = new StackPane(this.PreviewRectangle);
    
    private Stage _stage = null;
    
    public BoxShadow(Stage stage) {
        this._stage = stage;
        
        this.PreviewRectangle.setStroke(Color.BLACK);
        this.PreviewRectangle.setStrokeWidth(1);
        
        this.PreviewDropShadow.setBlurType(BlurType.GAUSSIAN);
        this.PreviewDropShadow.setColor(this.ShadowColor.getValue());
        this.PreviewInnerShadow.setColor(this.ShadowColor.getValue());
        
        super.setLeft(this.leftPane);
        super.setCenter(this.centerPane);
        
        this.leftPane.setPadding(new Insets(10));
        this.leftPane.getStyleClass().add("gradient-background");
        
        Label shadowTypeLabel = this.GetFixedWidthLabel("Outline", LABEL_SIZE);
        
        HBox shadowTypeHBox = new HBox();
        shadowTypeHBox.getChildren().addAll(shadowTypeLabel, this.BoxShadowTypeToggleGroup);
        
        HBox shadowColorHBox = new HBox(5);
        shadowColorHBox.getChildren().addAll(this.GetFixedWidthLabel("Shadow Color", LABEL_SIZE), this.ShadowColor);
        
        HBox boxColorHBox = new HBox(5);
        boxColorHBox.getChildren().addAll(this.GetFixedWidthLabel("Background Color", LABEL_SIZE), this.BoxColor);

        this.leftPane.getChildren().addAll(
                shadowTypeHBox,
                
                this.GetVerticalSeperator(SEPERATOR_HEIGHT),
                
                this.HorizontalLength,
                this.VerticalLength,
                
                this.GetVerticalSeperator(SEPERATOR_HEIGHT),
                
                this.BlurRadius,
                this.SpreadRadius,
                
                this.GetVerticalSeperator(SEPERATOR_HEIGHT),
                
                shadowColorHBox,
                boxColorHBox
        );

        centerPane.setCenter(previewPane);
        
        ToolBar previewToolBar = new ToolBar(new Label("Preview"));
        centerPane.setTop(previewToolBar);
        
        ToolBar exportToolBar = new ToolBar(
                new Label("Export to "),
                this.PngButton,
                this.CssButton
        );
        
        centerPane.setBottom(exportToolBar);

        this.SetEffect(this.PreviewDropShadow);
        
        // Event
        this.HorizontalLengthProperty.bindBidirectional(this.PreviewDropShadow.offsetXProperty());
        this.VerticalLengthProperty.bindBidirectional(this.PreviewDropShadow.offsetYProperty());
        this.BlurRadiusProperty.bindBidirectional(this.PreviewDropShadow.radiusProperty());
        this.SpreadRadiusProperty.bindBidirectional(this.PreviewDropShadow.spreadProperty());
        
        this.HorizontalLengthProperty.bindBidirectional(this.PreviewInnerShadow.offsetXProperty());
        this.VerticalLengthProperty.bindBidirectional(this.PreviewInnerShadow.offsetYProperty());
        this.BlurRadiusProperty.bindBidirectional(this.PreviewInnerShadow.radiusProperty());
        this.SpreadRadiusProperty.bindBidirectional(this.PreviewInnerShadow.chokeProperty());
        
        this.ShadowColor.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                PreviewDropShadow.setColor(newValue);
                PreviewInnerShadow.setColor(newValue);
            }
        });
        
        this.BoxColor.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                PreviewRectangle.setFill(newValue);
            }
        });
        
        this.BoxShadowTypeToggleGroup.toggleGroupProperty().getValue().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle selectedToggle) {
                if (selectedToggle != null) {
                    ToggleButton toggle = (ToggleButton) selectedToggle;
                    
                    if (toggle.getText().toLowerCase().equals("outline")) {
                        SetEffect(PreviewDropShadow);
                    }
                    else {
                        SetEffect(PreviewInnerShadow);
                    }
                }
            }
        });
        
        this.PngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CommonTools.EXPORT_TO_IMAGE("png", "Portable Network Graphics", JpgFileChooser, previewPane, _stage);
            }
        });
        
        this.CssButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String hValue = Integer.toString((int) HorizontalLength.PropertySlider.getValue());
                String vValue = Integer.toString((int) VerticalLength.PropertySlider.getValue());
                String radius = Integer.toString((int) BlurRadius.PropertySlider.getValue());
                String spread = Integer.toString((int) (SpreadRadius.PropertySlider.getValue() * 100));
                
                String red = Integer.toString((int) (ShadowColor.getValue().getRed() * 256));
                String green = Integer.toString((int) (ShadowColor.getValue().getGreen() * 256));
                String blue = Integer.toString((int) (ShadowColor.getValue().getBlue() * 256));
                String opacity = Double.toString(ShadowColor.getValue().getOpacity());
                
                String css = CommonTools.GET_UNIQUE_ID(".boxShadow") + " {\n\t box-shadow: " + (OutlineToggleButton.isSelected() ? "" : "inset ") + hValue + "px " + vValue + "px " + radius + "px " + spread + "px rgba(" + red + ", " + green + ", " + blue + ", " + opacity + "); \n}";
                
                ExportToText("css", "Cascading Style Sheets", CssFileChooser, css);
            }
        });
        
        this.OutlineToggleButton.setSelected(true);
    }
    
    public Pane GetVerticalSeperator(double size) {
        Pane seperator = new Pane();
        seperator.setPrefHeight(size);
        
        return seperator;
    }
    
    public Label GetFixedWidthLabel(String text, double width) {
        Label label = new Label(text);
        label.setMinWidth(width);
        label.setPrefWidth(width);
        
        return label;
    }
    
    public void SetEffect(Effect effect) {
        this.PreviewRectangle.setEffect(effect);
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
}
