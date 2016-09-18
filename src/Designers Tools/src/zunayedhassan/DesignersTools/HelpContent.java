/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author ZUNAYED_PC
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
