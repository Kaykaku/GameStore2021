/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Admin
 */
public class RoundedImageView {
    public static void RoundedImage(ImageView imageView,double radius) {
            // set a clip to apply rounded border to the original image.
            Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
            );
            clip.setArcWidth(radius);
            clip.setArcHeight(radius);
            imageView.setClip(clip);

            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = imageView.snapshot(parameters, null);

            imageView.setClip(null);

            imageView.setImage(image);
        }
}
