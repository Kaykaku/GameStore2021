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
public class BoxMove extends AnimationFX {

    /**
     * Create new Pulse
     *
     * @param node The node to affect
     */
    
    public BoxMove(Node node) {
        super(node);
    }

    public BoxMove() {
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
                new KeyFrame(Duration.millis(00),
                        new KeyValue(getNode().translateXProperty(), getNode().getTranslateX(), AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(1500),
                        new KeyValue(getNode().translateXProperty(), getNode().getTranslateX()<=-1200?0:getNode().getTranslateX()-305, AnimateFXInterpolator.EASE)
                )
        ));
    }
}
