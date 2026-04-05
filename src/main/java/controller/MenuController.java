package controller;

import model.Presentation;
import app.AboutBox;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuController extends MenuBar {

    private final Presentation presentation;

    protected static final String ABOUT = "About";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String HELP = "Help";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PAGENR = "Page number?";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";
    protected static final String VIEW = "View";

    public MenuController(Frame frame, Presentation pres) {
        this.presentation = pres;

        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    new OpenPresentationCommand(presentation, fileChooser.getSelectedFile().getAbsolutePath()).execute();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Failed to open presentation:\n" + ex.getMessage(),
                            "Error Opening File",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(e -> presentation.clear());

        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    new SavePresentationCommand(presentation, fileChooser.getSelectedFile().getAbsolutePath()).execute();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Failed to save presentation:\n" + ex.getMessage(),
                            "Error Saving File",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(e -> new ExitCommand().execute());
        add(fileMenu);

        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(e -> new NextSlideCommand(presentation).execute());

        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(e -> new PreviousSlideCommand(presentation).execute());

        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(e -> {
            String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
            try {
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            } catch (NumberFormatException ex) {
                // Ignore invalid input
            }
        });
        add(viewMenu);

        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(e -> AboutBox.show(frame));
        setHelpMenu(helpMenu);
    }

    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
