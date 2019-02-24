package zunayedhassan.DesignersTools;

import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Zunayed Hassan
 */
public class HelpContent extends BorderPane {
    public HelpContent() {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        
        String url = "file:///" + System.getProperty("user.dir") + "/help/index.html";
        url.replace("\\\\", "/");
        webEngine.load(url);
        
        this.setCenter(browser);
    }
}
