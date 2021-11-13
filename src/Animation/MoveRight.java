/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

/**
 *
 * @author Admin
 */
import animatefx.animation.AnimateFXInterpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.util.Duration;


public class MoveRight extends AnimationFX {

    public MoveRight(Node node,double distance) {
        super(node,distance);
    }

    public MoveRight() {
    }
    
    @Override
    AnimationFX resetNode() {
        getNode().setOpacity(1);
        getNode().setTranslateX(0);
        return this;
    }

    @Override
    void initTimeline() {
        setTimeline(new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(getNode().translateXProperty(), -distance, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(getNode().translateXProperty(), 0, AnimateFXInterpolator.EASE)
                )
        ));
    }
}
