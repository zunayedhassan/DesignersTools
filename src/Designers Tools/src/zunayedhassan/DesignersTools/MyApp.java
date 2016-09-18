/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Zunayed Hassan
 */
public class MyApp extends Application {
    private static MyApp _app = new MyApp();
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(CommonTools.GET_SCENE_AND_INITIALIZE(primaryStage));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static MyApp GET_INSTANCE() {
        return MyApp._app;
    }
}
