package controller;

import model.Presentation;
import persistence.XMLAccessor;

import java.io.IOException;

public class SavePresentationCommand implements Command {
    private Presentation presentation;
    private String filename;

    public SavePresentationCommand(Presentation presentation, String filename) {
        this.presentation = presentation;
        this.filename = filename;
    }

    @Override
    public void execute() {
        try {
            XMLAccessor accessor = new XMLAccessor();
            accessor.saveFile(presentation, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
