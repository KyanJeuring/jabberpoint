package controller;

import model.Presentation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {
    private Presentation presentation;

    public KeyController(Presentation p) {
        this.presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case '+':
                new NextSlideCommand(presentation).execute();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                new PreviousSlideCommand(presentation).execute();
                break;
            case 'q':
            case 'Q':
                new ExitCommand().execute();
                break;
            default:
                break;
        }
    }
}
