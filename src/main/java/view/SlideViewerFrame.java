package view;

import controller.KeyController;
import controller.MenuController;
import model.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SlideViewerFrame extends JFrame {

    private static final String JAB_TITLE = "Jabberpoint 1.6 - OU";

    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
        setupWindow(slideViewerComponent, presentation);
    }

    public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
        setTitle(JAB_TITLE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(slideViewerComponent);
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
        pack();
        setVisible(true);
    }
}
