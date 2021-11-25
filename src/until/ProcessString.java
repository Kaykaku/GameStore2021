/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import animatefx.animation.GlowText;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author Admin
 */
public class ProcessString {

    public static String cutString(String text, int limit) {
        text = text.length() > limit ? text.substring(0, limit - 2) + "..." : text;
        return text;
    }

    public static void showMessage(Label label, String mess) {
        GlowText ani = new GlowText(label, Color.BLUE, Color.LIGHTBLUE);
        ani.setCycleCount(3);
        ani.play();
        label.setOpacity(1);
        label.setText(mess + "");
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    label.setOpacity(0);
                });
            }
        }.start();
    }
}
