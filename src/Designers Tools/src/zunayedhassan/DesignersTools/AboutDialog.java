package zunayedhassan.DesignersTools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ZUNAYED_PC
 */
public class AboutDialog extends Dialog {
    public AboutDialog() {
        this.setTitle("About");
        
        if (!OSValidator.IS_UNIX()) {
            ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(new Image(this.getClass().getResourceAsStream("icons/help-about.png")));
        }
        
        VBox content = new VBox(5);
        
        content.setAlignment(Pos.CENTER);
        
        Text titleText = this.GetBoldText("\nDesigners Tools 1.0");
        titleText.setStyle("-fx-font-size: 18px;");
        
        GridPane creditsPane = new GridPane();

        content.getChildren().addAll(
                new ImageView(new Image(this.getClass().getResourceAsStream("icons/icon_small.png"))),
                titleText,
                new Text("License: GNU GPL 3"),
                new Text("\nA simple collection of tools to help you to"),
                new Text("be more creative"),
                this.GetBoldText("\nCredits"),
                creditsPane
        );
        
        creditsPane.add(this.GetBoldText("Developer"), 0, 0);
        
        creditsPane.add(new Text("Zunayed Hassan"), 0, 1);
        creditsPane.add(new Hyperlink("zunayed-hassan@live.com"), 1, 1);
        
        creditsPane.add(this.GetBoldText("\nGraphics"), 0, 2);
        
        creditsPane.add(new Text("Cheser Icon Theme"), 0, 3);
        creditsPane.add(new Hyperlink("https://github.com/chekavy/cheser-icon-theme"), 1, 3);
        
        creditsPane.add(new Text("Icon Finder"), 0, 4);
        creditsPane.add(new Hyperlink("https://www.iconfinder.com/"), 1, 4);
        
        creditsPane.add(new Text("Subtle Patterns"), 0, 5);
        creditsPane.add(new Hyperlink("http://subtlepatterns.com/"), 1, 5);
        
        
        for (Node node : creditsPane.getChildren()) {
            if (node instanceof Hyperlink) {
                Hyperlink link = (Hyperlink) node;
                link.setStyle("-fx-color: #3a8da5;");
                
                link.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String linkContent = link.getText();
                        
                        if (linkContent.contains("@")) {
                            linkContent = "mailto:" + linkContent;
                        }
                        
                        CommonTools.OPEN_DOCUMENT(linkContent);
                    }
                });
            }
        }
        
        this.getDialogPane().setContent(content);
        this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }
    
    public Text GetBoldText(String content) {
        Text text = new Text(content);
        text.setStyle("-fx-font-weight: bold;"); 
        
        return text;
    }
}
