/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zunayedhassan.DesignersTools;

import javafx.geometry.Side;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

/**
 *
 * @author Zunayed Hassan
 */
public class BackgroundImagePreviewer {
    public Pane Preview = null;
    
    public BackgroundImagePreviewer(Pane preview) {
        this.Preview = preview;
    }
    
    public void SetBackgroundPreview(
            String imagePath,
            double width,
            double height,
            String posX,
            String posY,
            String repeatX,
            String repeatY
    ) {
        
        Image image  = new Image("file:" + imagePath);
        BackgroundRepeat bgRepeatX = this._getCalculatedRepeat(repeatX);
        BackgroundRepeat bgRepeatY = this._getCalculatedRepeat(repeatY);
        
        double horizontalPosition = 0;
        double verticalPosition = 0;
        
        Side bgPosX = Side.LEFT;
        
        if (posX.equals("Left")) {
            bgPosX = Side.LEFT;
        }
        else if (posX.equals("Right")) {
            bgPosX = Side.LEFT;
            horizontalPosition = 1;
        }
        else {
            horizontalPosition = 0.5;
        }
        
        Side bgPosY = Side.TOP;
        
        if (posY.equals("Top")) {
            bgPosY = Side.TOP;
        }
        else if (posY.equals("Bottom")) {
            bgPosY = Side.TOP;
            verticalPosition = 1;
        }
        else {
            verticalPosition = 0.5;
        }
        
        this.Preview.setBackground(
                new Background(
                        new BackgroundImage(
                                image,
                                bgRepeatX,
                                bgRepeatY,
                                new BackgroundPosition(bgPosX, horizontalPosition, true, bgPosY, verticalPosition, true),
                                new BackgroundSize(width, height, false, false, false, false)
                        )
                )
        );
    }
    
    private BackgroundRepeat _getCalculatedRepeat(String repeat) {
        BackgroundRepeat bgRepeat = BackgroundRepeat.NO_REPEAT;
        
        if (repeat.equals("Repeat")) {
            bgRepeat = BackgroundRepeat.REPEAT;
        }
        else if (repeat.equals("No Repeat")) {
            bgRepeat = BackgroundRepeat.NO_REPEAT;
        }
        else if (repeat.equals("Round")) {
            bgRepeat = BackgroundRepeat.ROUND;
        }
        
        return bgRepeat;
    }
}
