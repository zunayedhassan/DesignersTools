/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import com.sun.javafx.scene.control.skin.CustomColorDialog;
import java.util.Optional;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.scene.text.Text;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScreenRuler extends BorderPane {
    private StackPane _base = new StackPane();
    
    public static final int DEFAULT_RULER_WIDTH = 800; 
    public static final int DEFAULT_RULER_HEIGHT = 70;
    public static final int MEASUREMENT_LINE_HEIGHT = 21;
    public static final double DPI = Screen.getPrimary().getDpi();
    public static final Color DEFAULT_RULER_COLOR = Color.web("#FFC266");
    
    public static enum UNIT {
        INCH,
        CENTIMETRE
    }

    public Pane Ruler = new Pane();
    public ContextMenu RulerContextMenu = new ContextMenu();
    public ColorChooserDialog BackgropundColorChooserDialog = new ColorChooserDialog("Change Background Color");
    public ColorChooserDialog FontColorChooserDialog = new ColorChooserDialog("Change Font Color");
    public Slider TransparencySlider = new Slider(0.1, 1, 1);
    public Slider RotationSlider = new Slider(-180, 180, 0);
    public Spinner<Double> RotationSpinner = new Spinner<>(this.RotationSlider.getMin(), this.RotationSlider.getMax(), this.RotationSlider.getValue());
    public HBox RotationPane = new HBox(5, this.RotationSlider, this.RotationSpinner);
    public Slider SizeSlider = new Slider(DPI, Screen.getPrimary().getBounds().getWidth(), DEFAULT_RULER_WIDTH);
    
    public RadioMenuItem InchMenuItem = new RadioMenuItem("Inch");
    public RadioMenuItem CmMenuItem = new RadioMenuItem("Centimetre");
    public ToggleGroup UnitToggleGroup = new ToggleGroup();
    public MenuItem HorizontalMenuItem = new MenuItem("Horizontal", new ImageView(new Image(this.getClass().getResourceAsStream("icons/gtk-orientation-landscape.png"))));
    public MenuItem VerticalMenuItem = new MenuItem("Vertical", new ImageView(new Image(this.getClass().getResourceAsStream("icons/gtk-orientation-portrait.png"))));
    public CustomMenuItem SizeMenuItem = new CustomMenuItem(this.GetLayoutFromGivenNode("Length", "icons/document-page-setup.png", this.SizeSlider, Side.LEFT));
    public CustomMenuItem RotateMenuItem = new CustomMenuItem(this.GetLayoutFromGivenNode("Rotate", "icons/object-rotate-right.png", this.RotationPane, Side.LEFT));
    public MenuItem ChangeBackColorMenuItem = new MenuItem("Background Color", new ImageView(new Image(this.getClass().getResourceAsStream("icons/color-fill.png"))));
    public MenuItem ChangeFontColorMenuItem = new MenuItem("Font Color", new ImageView(new Image(this.getClass().getResourceAsStream("icons/font-x-generic.png"))));
    public CustomMenuItem TransparencyMenuItem = new CustomMenuItem(this.GetLayoutFromGivenNode("Transparency", "icons/transparency.png", this.TransparencySlider, Side.LEFT));
    public RadioMenuItem AlwaysOnTopMenuItem = new RadioMenuItem("Always on Top", new ImageView(new Image(this.getClass().getResourceAsStream("icons/windows.png"))));
    public MenuItem MinimizeMenuItem = new MenuItem("Minimize", new ImageView(new Image(this.getClass().getResourceAsStream("icons/minimize.png"))));
    public MenuItem ExitMenuItem = new MenuItem("Close", new ImageView(new Image(this.getClass().getResourceAsStream("icons/Close.png"))));

    public UNIT Unit = UNIT.INCH;
    
    private Stage _stage = null;
    
    public ScreenRuler(Stage stage) {
        this._stage = stage;
        
        // Make windows transparent
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Adding components
        this.setCursor(Cursor.MOVE);
        this.setCenter(this._base);
        
        this._base.getChildren().add(this.Ruler);
        
        this.Ruler.setMaxWidth(DEFAULT_RULER_WIDTH);
        this.Ruler.setMaxHeight(DEFAULT_RULER_HEIGHT);
        this.Ruler.setBackground(new Background(new BackgroundFill(DEFAULT_RULER_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        this.RulerContextMenu.getItems().addAll(
                this.InchMenuItem,
                this.CmMenuItem,
                new SeparatorMenuItem(),
                this.HorizontalMenuItem,
                this.VerticalMenuItem,
                new SeparatorMenuItem(),
                this.SizeMenuItem,
                this.RotateMenuItem,
                new SeparatorMenuItem(),
                this.ChangeBackColorMenuItem,
                this.ChangeFontColorMenuItem,
                new SeparatorMenuItem(),
                this.TransparencyMenuItem,
                new SeparatorMenuItem(),
                this.AlwaysOnTopMenuItem,
                this.MinimizeMenuItem,
                this.ExitMenuItem
        );
        
        this.RotationSpinner.setPrefWidth(70);
        
        this.UnitToggleGroup.getToggles().addAll(
                this.InchMenuItem,
                this.CmMenuItem
        );
        
        // Events
        this.Ruler.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    RulerContextMenu.show(_stage, event.getScreenX(), event.getScreenY());
                }
            }
        });
        
        this.ExitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                _stage.close();
            }
        });
        
        this.HorizontalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ruler.setRotate(0);
            }
        });
        
        this.VerticalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ruler.setRotate(-90);
            }
        });
        
        this.SizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Ruler.setMaxWidth(newValue.doubleValue());
                UpdateMeasurementRuler();
            }
        });
        
        this.Ruler.rotateProperty().bindBidirectional(this.RotationSlider.valueProperty());
        
        this.Ruler.rotateProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                RotationSpinner.getValueFactory().setValue(newValue.doubleValue());
            }
        });
        
        this.RotationSpinner.getValueFactory().valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                Ruler.setRotate(newValue.doubleValue());
            }
        });
        
        this.ChangeBackColorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BackgropundColorChooserDialog.show();
            }
        });
        
        
        this.ChangeBackColorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BackgropundColorChooserDialog.ColorsContent.ColorChooserBaseControl.SetCustomColor((Color) Ruler.getBackground().getFills().get(0).getFill());
                
                Optional<Color> result = BackgropundColorChooserDialog.showAndWait();
                
                if (result.isPresent()) {
                    Color choosenColor = result.get();
                    Ruler.setBackground(new Background(new BackgroundFill(choosenColor, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        });
        
        this.ChangeFontColorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color currentColor = null;
                
                if (Ruler.getChildren().get(0) instanceof Line) {
                    currentColor = (Color) ((Line) Ruler.getChildren().get(0)).getStroke();
                }
                else {
                    currentColor = (Color) ((Text) Ruler.getChildren().get(0)).getFill();
                }
                
                FontColorChooserDialog.ColorsContent.ColorChooserBaseControl.SetCustomColor(currentColor);
                
                Optional<Color> result = FontColorChooserDialog.showAndWait();
                
                if (result.isPresent()) {
                    Color choosenColor = result.get();
                    
                    for (Node node : Ruler.getChildren()) {
                        if (node instanceof Text) {
                            Text text = (Text) node;
                            text.setFill(choosenColor);
                        }
                        else if (node instanceof Line) {
                            Line line = (Line) node;
                            line.setStroke(choosenColor);
                        }
                    }
                }
            }
        });
        
        this.TransparencySlider.valueProperty().bindBidirectional(this.Ruler.opacityProperty());
        
        this.UnitToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue != null) {
                    RadioMenuItem selectedToggle = (RadioMenuItem) newValue;

                    if (selectedToggle.getText().toLowerCase().equals("inch")) {
                        Unit = UNIT.INCH;
                    }
                    else {
                        Unit = UNIT.CENTIMETRE;
                    }
                    
                    UpdateMeasurementRuler();
                }
            }
        });
        
        this.MinimizeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                _stage.setIconified(true);
            }
        });
        
        this.AlwaysOnTopMenuItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                _stage.setAlwaysOnTop(isSelected);
            }
        });
        
        this.InchMenuItem.setSelected(true);
        this.AlwaysOnTopMenuItem.setSelected(true);
    }
    
    public void UpdateMeasurementRuler() {
        Color currentColor = Color.BLACK;
        double unitConstant = (Unit == UNIT.INCH) ? 1 : 2.54;
        
        if (this.Ruler.getChildren().size() > 0) {
            if (this.Ruler.getChildren().get(0) instanceof Text) {
                currentColor = (Color) ((Text) this.Ruler.getChildren().get(0)).getFill();
            }
            else {
                currentColor = (Color) ((Line) this.Ruler.getChildren().get(0)).getStroke();
            }
        }
        
        this.Ruler.getChildren().clear();
        
        Text unitTitleText = new Text((this.Unit == UNIT.INCH) ? "Inch" : "Centimetre");
        unitTitleText.setTranslateX(15);
        unitTitleText.setTranslateY(15);
        this.Ruler.getChildren().add(unitTitleText);
        
        for (int i = 0; i < (this.Ruler.getMaxWidth() / (DPI / unitConstant)); i++) {
            Line line = new Line(
                    i * (DPI / unitConstant), 
                    DEFAULT_RULER_HEIGHT - MEASUREMENT_LINE_HEIGHT, 
                    i * (DPI / unitConstant), 
                    DEFAULT_RULER_HEIGHT
            );
            
            line.setStroke(currentColor);
            line.setStrokeWidth(1);

            boolean isSmallLine = true;
            double heightDifferenceFactor = 2;
            int totalNumberOfSubline = (this.Unit == UNIT.INCH) ? 7 : 10;

            for (int j = 1; j <= totalNumberOfSubline; j++) {                    
                if (isSmallLine) {
                    heightDifferenceFactor = 2;
                }
                else {
                    heightDifferenceFactor = 1.5;
                }

                isSmallLine = !isSmallLine;

                Line subLine = new Line(
                        j * ((DPI / unitConstant) / (totalNumberOfSubline + 1)) + line.getStartX(),
                        DEFAULT_RULER_HEIGHT - (MEASUREMENT_LINE_HEIGHT / heightDifferenceFactor),
                        j * ((DPI / unitConstant) / (totalNumberOfSubline + 1)) + line.getStartX(),
                        DEFAULT_RULER_HEIGHT
                );
                
                subLine.setStroke(currentColor);
                subLine.setStrokeWidth(0.5);
                
                if (subLine.getStartX() < this.Ruler.getMaxWidth()) {
                    this.Ruler.getChildren().add(subLine);
                }
            }
            
            Text label = new Text(Integer.toString(i));
            label.setFill(currentColor);
            label.setTranslateX(line.getStartX() + 2);
            label.setTranslateY(line.getStartY() - 6);
            
            this.Ruler.getChildren().add(label);
            this.Ruler.getChildren().add(line);
        }
    }
    
    public Pane GetLayoutFromGivenNode(String title, String icon, Node node, Side side) {
        Pane pane = null;
        
        Label titleLabel = new Label(title, new ImageView(new Image(this.getClass().getResourceAsStream(icon))));
        titleLabel.setPrefWidth(120);
        
        switch (side) {
            case LEFT:
                HBox hbox = new HBox(5, titleLabel, node);
                hbox.setAlignment(Pos.CENTER);
                pane = hbox;
                break;
                
            case TOP:
                VBox vbox = new VBox(5, titleLabel, node);
                pane = vbox;
                break;
        }
        
        return pane;
    }
}
