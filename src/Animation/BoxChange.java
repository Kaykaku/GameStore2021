/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;


import animatefx.animation.AnimateFXInterpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;


/**
 * @author LoÃ¯c Sculier
 */
public class BoxChange extends AnimationFX {

    /**
     * Create new Pulse
     *
     * @param node The node to affect
     */
    
    public BoxChange(Node node) {
        super(node);
    }

    public BoxChange() {
    }


    @Override
    AnimationFX resetNode() {
        getNode().setScaleX(1);
        getNode().setScaleY(1);
        getNode().setScaleZ(1);
        return this;
    }

    @Override
    void initTimeline() {
        setTimeline(new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(getNode().scaleXProperty(), 0, AnimateFXInterpolator.EASE)
                ),                   
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(getNode().scaleXProperty(), 26, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(4000),
                        new KeyValue(getNode().scaleXProperty(), 0, AnimateFXInterpolator.EASE)
                )
        ));
    }
}
