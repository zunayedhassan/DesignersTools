package zunayedhassan.DesignersTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javafx.stage.Stage;

/**
 *
 * @author Zunayed Hassan
 */
public class BackgroundImageEditor extends BorderPane {
    public static final double PREVIEW_SIZE = 128;
    
    private String[][] _sampleBackgrounImageList = new String[][] {
        { "Asanoha", "images/Sample Background Images/asanoha-400px.png" },
        { "Cheap diagonal fabric", "images/Sample Background Images/cheap_diagonal_fabric.png" },
        { "Concrete seamless", "images/Sample Background Images/concrete_seamless.png" },
        { "Confectionary", "images/Sample Background Images/confectionary.png" },
        { "Congruent outline", "images/Sample Background Images/congruent_outline.png" },
        { "Congruent pentagon", "images/Sample Background Images/congruent_pentagon.png" },
        { "Contemporary china", "images/Sample Background Images/contemporary_china.png" },
        { "Contemporary china 2", "images/Sample Background Images/contemporary_china_2.png" },
        { "Corkwallet", "images/Sample Background Images/cork-wallet.png" },
        { "Dark embroidery", "images/Sample Background Images/dark_embroidery.png" },
        { "Eight horns", "images/Sample Background Images/eight_horns.png" },
        { "Food", "images/Sample Background Images/food.png" },
        { "Footer lodyas", "images/Sample Background Images/footer_lodyas.png" },
        { "Giftly", "images/Sample Background Images/giftly.png" },
        { "Green cup", "images/Sample Background Images/green_cup.png" },
        { "Halftone", "images/Sample Background Images/halftone.png" },
        { "Ignasi pattern s", "images/Sample Background Images/ignasi_pattern_s.png" },
        { "Logo x pattern", "images/Sample Background Images/logo_x_pattern.png" },
        { "New year background", "images/Sample Background Images/new_year_background.png" },
        { "Paisley", "images/Sample Background Images/paisley.png" },
        { "Pink rice", "images/Sample Background Images/pink_rice.png" },
        { "Restaurant", "images/Sample Background Images/restaurant.png" },
        { "Restaurant icons", "images/Sample Background Images/restaurant_icons.png" },
        { "Sativa", "images/Sample Background Images/sativa.png" },
        { "Sayagata", "images/Sample Background Images/sayagata-400px.png" },
        { "School", "images/Sample Background Images/school.png" },
        { "Seamless paper texture", "images/Sample Background Images/seamless_paper_texture.png" },
        { "Seigaiha", "images/Sample Background Images/seigaiha.png" },
        { "Skulls", "images/Sample Background Images/skulls.png" },
        { "Small steps", "images/Sample Background Images/small_steps.png" },
        { "Subtle white mini waves", "images/Sample Background Images/subtle_white_mini_waves.png" },
        { "Swirl pattern", "images/Sample Background Images/swirl_pattern.png" },
        { "Symphony", "images/Sample Background Images/symphony.png" },
        { "Upfeathers", "images/Sample Background Images/upfeathers.png" },
        { "Weather", "images/Sample Background Images/weather.png" },
        { "Wov", "images/Sample Background Images/wov.png" }
    };
    
    public Pane Preview = new Pane();
    public Label OriginalImageWidth = new Label("0");
    public Label OriginalImageHeight = new Label("0");
    public BackgroundImagePreviewer BackgroundUpdater = new BackgroundImagePreviewer(this.Preview);
    public Spinner<Integer> ImageWidthSpinner = new Spinner<>(0, Integer.MAX_VALUE, 128);
    public Spinner<Integer> ImageHeightSpinner = new Spinner<>(0, Integer.MAX_VALUE, 128);
    public OpenImagePane BgOpenImagePane = new OpenImagePane(this.BackgroundUpdater, this.OriginalImageWidth, this.OriginalImageHeight, this.ImageWidthSpinner, this.ImageHeightSpinner);
    public PropertyElementWithComboBox HorizontalPosition = new PropertyElementWithComboBox(new String[] { "Left", "Center", "Right" }, 150);
    public PropertyElementWithComboBox VerticalPosition = new PropertyElementWithComboBox(new String[] { "Top", "Center", "Bottom" }, 150);
    public PropertyElementWithComboBox RepeatX = new PropertyElementWithComboBox(new String[] { "No Repeat", "Repeat", "Round" }, 150);
    public PropertyElementWithComboBox RepeatY = new PropertyElementWithComboBox(new String[] { "No Repeat", "Repeat", "Round" }, 150);    
    public FileChooser PngFileChooser = new FileChooser();
    public FileChooser SvgFileChooser = new FileChooser();
    public FileChooser CssFileChooser = new FileChooser();
    
    private Stage _stage = null;
    
    public BackgroundImageEditor(Stage stage) {
        this._stage = stage;
        this.BgOpenImagePane.SetStage(this._stage);
        
        BorderPane leftBorderPane = new BorderPane();
        this.setLeft(leftBorderPane);
        
        BorderPane samplesBackgroundBorderPane = this.GetSamplesBackgroundPane();
        BorderPane propertyBackgroundBorderPane = this.GetBackgroundImagePane();
        BorderPane previewPane = this.GetPreviewPane();
        
        leftBorderPane.setLeft(samplesBackgroundBorderPane);
        leftBorderPane.setCenter(propertyBackgroundBorderPane);
        this.setCenter(previewPane);
        
        this.SvgFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Scalable Vector Graphics", "*.svg"));
        this.CssFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Cascading Style Sheets", "*.css"));
    }
    
    public BorderPane GetSamplesBackgroundPane() {
        BorderPane samplesBorderPane = new BorderPane();
        
        Label titleLabel = this.GetBoldLabel("Samples");
        ToolBar titleToolBar = new ToolBar(titleLabel);
        
        samplesBorderPane.setTop(titleToolBar);

        ListView<SampleBackgroundImageListElement> listView = new ListView<>();
        samplesBorderPane.setCenter(listView);
        
        ObservableList<SampleBackgroundImageListElement> list = FXCollections.observableArrayList();
        
        for (String[] item : this._sampleBackgrounImageList) {
            list.add(new SampleBackgroundImageListElement(
                    item[0],
                    item[1],
                    this.Preview,
                    this.BgOpenImagePane,
                    this.OriginalImageWidth,
                    this.OriginalImageHeight,
                    this.ImageWidthSpinner,
                    this.ImageHeightSpinner
                )
            );
        }
        
        listView.setItems(list);
        
        return samplesBorderPane;
    }
    
    public BorderPane GetBackgroundImagePane() {
        BorderPane base = new BorderPane();
        
        Label titleLabel = this.GetBoldLabel("Properties");
        ToolBar titleToolBar = new ToolBar(titleLabel);
        
        ToolBar mainPane = new ToolBar();

        VBox vbox = new VBox(5);
        mainPane.getItems().add(vbox);
        
        GridPane originalImageDimensionInformationPane = new GridPane();
        originalImageDimensionInformationPane.add(this.GetBoldLabel("Original Image Width: "), 0, 0);
        originalImageDimensionInformationPane.add(this.GetBoldLabel("Original Image Height: "), 0, 1);
        originalImageDimensionInformationPane.add(this.OriginalImageWidth, 1, 0);
        originalImageDimensionInformationPane.add(this.OriginalImageHeight, 1, 1);
        originalImageDimensionInformationPane.add(new Label(" px"), 2, 0);
        originalImageDimensionInformationPane.add(new Label(" px"), 2, 1);
        
        vbox.getChildren().addAll(
                new AdvancedPropertyElement("Background Image", this.BgOpenImagePane),
                
                this.GetSeperator(),
                
                originalImageDimensionInformationPane,
                
                this.GetSeperator(),
                
                this.GetBoldLabel("Size"),
                new AdvancedPropertyElementWithSpinner("Background Width", this.ImageWidthSpinner),
                new AdvancedPropertyElementWithSpinner("Background Height", this.ImageHeightSpinner),
                
                this.GetSeperator(),
                
                this.GetBoldLabel("Background Horizontal"),
                new AdvancedPropertyElement("Position X", this.HorizontalPosition),
                new AdvancedPropertyElement("Repeat X", this.RepeatX),
                
                this.GetSeperator(),
                
                this.GetBoldLabel("Background Vertical"),
                new AdvancedPropertyElement("Position Y", this.VerticalPosition),
                new AdvancedPropertyElement("Repeat Y", this.RepeatY)
        );
        
        mainPane.setPrefHeight(Settings.WINDOW_WIDTH);
        
        base.setTop(titleToolBar);
        base.setCenter(mainPane);
        
        // Events
        this.ImageWidthSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                Update();
            }
        });
        
        this.ImageHeightSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer width) {
                Update();
            }
        });
        
        this.ImageWidthSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Update();
            }
        });
        
        this.ImageHeightSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Update();
            }
        });
        
        this.HorizontalPosition.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Update();
            }
        });
        
        this.VerticalPosition.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Update();
            }
        });
        
        this.RepeatX.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Update();
            }
        });
        
        this.RepeatY.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Update();
            }
        });

        return base;
    }
    
    public BorderPane GetPreviewPane() {
        BorderPane base = new BorderPane();
        
        Label titleLabel = this.GetBoldLabel("Preview");
        ToolBar titleToolBar = new ToolBar(titleLabel);

        this.Preview.setMaxSize(PREVIEW_SIZE, PREVIEW_SIZE);
        this.Preview.setPrefSize(PREVIEW_SIZE, PREVIEW_SIZE);
        this.Preview.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        
        StackPane stackPane = new StackPane(this.Preview);
        stackPane.setPadding(new Insets(0, 25, 0, 25));
        
        base.setCenter(stackPane);
        base.setTop(titleToolBar);
        
        ToolBar bottomToolBar = new ToolBar();
        base.setBottom(bottomToolBar);
        
        Button exportPngButton = new Button("Png");
        Button exportSvgButton = new Button("SVG");
        Button exportCssButton = new Button("CSS");
        
        bottomToolBar.getItems().addAll(
                exportPngButton,
                exportSvgButton,
                exportCssButton
        );
        
        // Events
        exportPngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExportToImage("png", "Portable Network Graphics", PngFileChooser);
            }
        });
        
        exportSvgButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExportToSvg(SvgFileChooser);
            }
        });
        
        exportCssButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExportToCss(CssFileChooser);
            }
        });
        
        return base;
    }
    
    public String GetHexColorString(Color color) {
        return ("#" + color.toString().substring(2, color.toString().length() - 2));
    }
    
    public String GetUniqueId(String prefix) {
        return (prefix + (new Date()).getTime());
    }
    
    public Pane GetSeperator() {
        Pane pane = new Pane();
        
        pane.setMinHeight(15);
        pane.setPrefHeight(15);
        
        return pane;
    }
    
    public Label GetBoldLabel(String text) {
        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-font-weight: bold;");
        
        return textLabel;
    }
    
    public void Update() {
        String hpos = "Center";
        String vpos = "Center";
        String repX = "Repeat";
        String repY = "Repeat";

        if (HorizontalPosition.getSelectionModel().getSelectedIndex() != -1) {
            hpos = HorizontalPosition.List.get(HorizontalPosition.getSelectionModel().getSelectedIndex());
        }

        if (VerticalPosition.getSelectionModel().getSelectedIndex() != -1) {
            vpos = VerticalPosition.List.get(VerticalPosition.getSelectionModel().getSelectedIndex());
        }

        if (RepeatX.getSelectionModel().getSelectedIndex() != -1) {
            repX = RepeatX.List.get(RepeatX.getSelectionModel().getSelectedIndex());
        }

        if (RepeatY.getSelectionModel().getSelectedIndex() != -1) {
            repY = RepeatY.List.get(RepeatY.getSelectionModel().getSelectedIndex());
        }

        BackgroundUpdater.SetBackgroundPreview(
                this.BgOpenImagePane.ImagePathTextField.getText(),
                this.ImageWidthSpinner.getValue(),
                this.ImageHeightSpinner.getValue(),
                hpos,
                vpos,
                repX,
                repY);
    }
    
    public void ExportToImage(String format, String description, FileChooser fileChooser) {
        WritableImage snapshot = this.Preview.snapshot(new SnapshotParameters(), null);
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
    
    public void ExportToCss(FileChooser fileChooser) {
        if (this.BgOpenImagePane.ImageFile != null) {
            String cssId = this.GetUniqueId(".background");
            
            String repX = "repeat";
            
            if (this.RepeatX.getSelectionModel().getSelectedIndex() != -1) {
                if (this.RepeatX.getSelectionModel().getSelectedItem().equals("No Repeat")) {
                    repX = "no-repeat";
                }
                else if (this.RepeatX.getSelectionModel().getSelectedItem().equals("Repeat")) {
                    repX = "repeat";
                }
                else if (this.RepeatX.getSelectionModel().getSelectedItem().equals("Round")) {
                    repX = "round";
                }
            }

            String repY = "repeat";
            
            if (this.RepeatY.getSelectionModel().getSelectedIndex() != -1) {
                if (this.RepeatY.getSelectionModel().getSelectedItem().equals("No Repeat")) {
                    repY = "no-repeat";
                }
                else if (this.RepeatY.getSelectionModel().getSelectedItem().equals("Repeat")) {
                    repY = "repeat";
                }
                else if (this.RepeatY.getSelectionModel().getSelectedItem().equals("Round")) {
                    repY = "round";
                }
            }
            
            String css = cssId + " {\n\r\tbackground-image: url('" + this.BgOpenImagePane.ImageFile.getName() + "');\n\r\tbackground-repeat: " + repX + " " + repY + ";\n\r\tbackground-position: center;\n\r\tbackground-size:" + Integer.toString(this.ImageWidthSpinner.getValue()) + "px " + Integer.toString(this.ImageHeightSpinner.getValue()) + "px\n\r}";
            
            File cssDestination = this.GetFileAfterExportToText("css", "Cascading Style Sheets", this.CssFileChooser, css);

            if (cssDestination != null) {
                File source = new File(this.BgOpenImagePane.ImageFile.getPath().split(":")[1]);
                File destinitation = new File(cssDestination.getParent() + "/" + source.getName());
                this._copyFileUsingFileChannels(source, destinitation);
            }   
        }
    }
    
    public void ExportToSvg(FileChooser SvgFileChooser) {
        if (this.BgOpenImagePane.ImageFile != null) {
            final String patternId = this.GetUniqueId("pattern");
            final String imageId = this.GetUniqueId("image");
            final String rectangleId = this.GetUniqueId("rect");
            final String imageAddress = (new File(this.BgOpenImagePane.ImageFile.getPath().split(":")[1])).getName();
            
            String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
"<svg\n" +
"   xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
"   xmlns:cc=\"http://creativecommons.org/ns#\"\n" +
"   xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
"   xmlns:svg=\"http://www.w3.org/2000/svg\"\n" +
"   xmlns=\"http://www.w3.org/2000/svg\"\n" +
"   xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
"   version=\"1.1\">\n" +
"  <defs>\n" +
"    <pattern\n" +
"       id=\"" + patternId + "\"\n" +
"       height=\"" + Integer.toString(this.ImageHeightSpinner.getValue()) + "\"\n" +
"       width=\"" + Integer.toString(this.ImageWidthSpinner.getValue()) + "\"\n" +
"       patternUnits=\"userSpaceOnUse\">\n" +
"      <image\n" +
"         xlink:href=\"" + imageAddress + "\"\n" +
"         width=\"" + Integer.toString(this.ImageWidthSpinner.getValue()) + "\"\n" +
"         height=\"" + Integer.toString(this.ImageHeightSpinner.getValue()) + "\"\n" +
"         preserveAspectRatio=\"none\"\n" +
"         id=\"" + imageId + "\"\n" +
"         x=\"0\"\n" +
"         y=\"0\" />\n" +
"    </pattern>\n" +
"  </defs>\n" +
"  <g>\n" +
"    <rect\n" +
"       y=\"0\"\n" +
"       x=\"0\"\n" +
"       height=\"128\"\n" +
"       width=\"128\"\n" +
"       id=\"" + rectangleId + "\"\n" +
"       style=\"fill:url(#" + patternId + ");\" />\n" +
"  </g>\n" +
"</svg>";
            
            File svgDestinition = this.GetFileAfterExportToText("svg", "Scalable Vector Graphics", this.SvgFileChooser, svg);
            
            if (svgDestinition != null) {
                File source = new File(this.BgOpenImagePane.ImageFile.getPath().split(":")[1]);
                File destinitation = new File(svgDestinition.getParent() + "/" + source.getName());
                this._copyFileUsingFileChannels(source, destinitation);
            }
        }
    }
    
    public File GetFileAfterExportToText(String format, String description, FileChooser fileChooser, String text) {
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
                
                return file;
            }
        }
        
        return null;
    }
    
    private static void _copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        
	try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            
            inputChannel.close();
            outputChannel.close();
	}
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
