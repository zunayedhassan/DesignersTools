package zunayedhassan.DesignersTools;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import static zunayedhassan.DesignersTools.LinearGradientDetailsPane.GRADIENT_PREVIEW_WIDTH;

/**
 *
 * @author Zunayed Hassan
 */
public class RadialGradientStop extends StackPane {
    public static final Color MOUSE_ENTER_COLOR = Color.CORNFLOWERBLUE;
    public static final Color MOUSE_EXIT_COLOR = Color.web("#CCCCCC");
    
    public Circle Preview = new Circle(5);
    public double PositionX = 0;
    
    private boolean _isSelected = false;
    private Pane _parent = null;
    private ObservableList<Stop> _stops = null;
    private LinearGradient _gradient = null;
    private Rectangle _preview = null;
    private Stop _stop = null;
    private Color _color = null;
    private ColorChooser _colorChooser = null;
    private Rectangle _gradientPreview = null;
    private ObjectPropertyElement[] _propertyElements = null;
    
    protected double orgSceneX = 0;
    protected double orgSceneY = 0;
    protected double orgTranslateX = 0;
    protected double orgTranslateY = 0;
    
    protected double startX = Integer.MAX_VALUE;
    protected double startY = Integer.MAX_VALUE;
    protected double endX = Integer.MIN_VALUE;
    protected double endY = Integer.MIN_VALUE;
    
    public RadialGradientStop(Color color, double positionX, double parentWidth, Pane parent, ObservableList<Stop> stops, LinearGradient gradient, Rectangle preview, ColorChooser colorChooser, Rectangle gradientPreview, ObjectPropertyElement[] propertyElements) {
        this._parent = parent;
        this.PositionX = positionX * GRADIENT_PREVIEW_WIDTH;
        this._stops = stops;
        this._gradient = gradient;
        this._preview = preview;
        this._color = color;
        this._colorChooser = colorChooser;
        this._gradientPreview = gradientPreview;
        this._propertyElements = propertyElements;
        
        this.setPadding(new Insets(2));
        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(50), Insets.EMPTY)));
        
        this.Preview.setFill(color);
        this.Preview.setStrokeWidth(1);
        
        this.getChildren().add(this.Preview);
        
        this.setCursor(Cursor.HAND);
        this.setEffect(new DropShadow(3, 1, 1, Color.LIGHTGRAY));
        
        this.SetPosition(this.PositionX);
        this.SetMouseOverColor(MOUSE_EXIT_COLOR);
        
        this.setTranslateY(-this.GetRadius() * 1.5);
        
        this._stop = new Stop(this.GetOffset(), color);
        this._stops.add(this._stop);
        
        this.UpdateGradient();
        
        // Events
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!IsSelected()) {
                    SetMouseOverColor(MOUSE_ENTER_COLOR);
                }
            }
        });
        
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!IsSelected()) {
                    SetMouseOverColor(MOUSE_EXIT_COLOR);
                }
            }
        });
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneX = event.getSceneX();
                orgTranslateX = getTranslateX();
                
                // Deselect All
                for (Node node : _parent.getChildren()) {
                    if (node instanceof RadialGradientStop) {
                        RadialGradientStop gradientButton = (RadialGradientStop) node;
                        gradientButton.SetDeselect();
                    }
                }
                
                // Select this
                SetSelect();
                
                event.consume();
            }
        });
        
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - orgSceneX;
                double newTranslateX = orgTranslateX + offsetX;
                
                if ((newTranslateX >= 0 - GetRadius()) && (newTranslateX <= parentWidth - GetRadius())) {
                    setTranslateX(newTranslateX);
                }
            }
        });
        
        this.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number posX) {
                double offest = ((posX.doubleValue() + GetRadius()) / GRADIENT_PREVIEW_WIDTH);
                Stop newStop = new Stop(offest, GetColor());
                
                SetStop(newStop);
            }
        });
    }
    
    public void SetPosition(double positionX) {
        this.setTranslateX(positionX - this.GetRadius());
    }
    
    public void SetMouseOverColor(Color color) {
        this.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(50), new BorderWidths(1))));
        this.Preview.setStroke(color);
    } 
    
    public double GetRadius() {
        return this.Preview.getRadius() + this.getInsets().getLeft();
    }
    
    public void SetSelect() {
        this._isSelected = true;
        
        this.setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, new CornerRadii(50), new BorderWidths(1))));
        this.Preview.setStroke(Color.ORANGE);
        
        this._colorChooser.ColorChooserBaseControl.SetCustomColor(GetColor());
    }
    
    public void SetDeselect() {
        this._isSelected = false;
        SetMouseOverColor(MOUSE_EXIT_COLOR);
    }
    
    public boolean IsSelected() {
        return this._isSelected;
    }
    
    public double GetOffset() {
        return ((this.getTranslateX() + this.GetRadius()) / GRADIENT_PREVIEW_WIDTH);
    }
    
    public void UpdateGradient() {
        this._gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, this._stops);
        this._preview.setFill(this._gradient);

        double focusAngle = this._propertyElements[0].GetValue();
        double focusDistance = this._propertyElements[1].GetValue() / 100.0;
        double radius = this._propertyElements[2].GetValue() / 100.0;
        double centerX = this._propertyElements[3].GetValue() / 100.0;
        double centerY = this._propertyElements[4].GetValue() / 100.0;
        
        boolean proportional = true;
        CycleMethod cycleMethod = CycleMethod.NO_CYCLE;

        RadialGradient mainGradient = new RadialGradient(focusAngle, focusDistance, centerX, centerY, radius, proportional, cycleMethod, this._stops);

        this._gradientPreview.setFill(mainGradient);
    }
    
    public Stop GetStop() {
        return this._stop;
    }
    
    public Color GetColor() {
        return this._color;
    }
    
    public void SetColor(Color color) {
        this._color = color;
        this.Preview.setFill(this._color);
        
        Stop newStop = new Stop(this.GetOffset(), color);
        
        this.SetStop(newStop);
    }
    
    public void SetStop(Stop newStop) {
        for (int i = 0; i < this._stops.size(); i++) {
            Stop currentStop = this._stops.get(i);
            
            if (currentStop.equals(GetStop())) {
                this._stops.set(i, newStop);
                this._stop = newStop;
                
                break;
            }
        }
        
        this.UpdateGradient();
    }
}
