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
import javafx.scene.Node;
import javafx.util.Duration;


/**
 * @author LoÃ¯c Sculier aka typhon0
 */
public class BoxHoverMoveBack extends AnimationFX {

    /**
     * Create new SlideOutUp
     *
     * @param node The node to affect
     */
    public BoxHoverMoveBack(Node node) {
        super(node);
    }

    public BoxHoverMoveBack() {
    }

    @Override
    AnimationFX resetNode() {
        getNode().setOpacity(1);
        getNode().setTranslateY(0);
        return this;
    }

    @Override
    void initTimeline() {
        setTimeline(new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(getNode().translateYProperty(), 0, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(50),
                        new KeyValue(getNode().translateYProperty(), -1, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(100),
                        new KeyValue(getNode().translateYProperty(), -2, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(150),
                        new KeyValue(getNode().translateYProperty(), -3, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(getNode().translateYProperty(), -4, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(250),
                        new KeyValue(getNode().translateYProperty(), -3, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(getNode().translateYProperty(), -2, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(350),
                        new KeyValue(getNode().translateYProperty(), -1, AnimateFXInterpolator.EASE)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(getNode().translateYProperty(), 0, AnimateFXInterpolator.EASE)
                )
        ));
    }
}

