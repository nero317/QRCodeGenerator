package org.nero;

import javax.swing.*;

public class Main {

    public final static int SCREEN_WIDTH = 600;
    public final static int SCREEN_HEIGHT = 300;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QRGeneratorApp app = new QRGeneratorApp();
            app.setVisible(true);
        });
    }
}