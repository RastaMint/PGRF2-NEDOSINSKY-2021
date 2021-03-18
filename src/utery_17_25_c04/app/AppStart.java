package utery_17_25_c04.app;

import utery_17_25_c04.controller.Controller3D;
import utery_17_25_c04.view.Window;

import javax.swing.*;

public class AppStart {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            new Controller3D(window.getPanel());
            window.setVisible(true); // zobrazit okno
        });
        // https://www.google.com/search?q=SwingUtilities.invokeLater
        // https://www.javamex.com/tutorials/threads/invokelater.shtml
    }

}
