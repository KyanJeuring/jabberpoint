package controller;

import model.Presentation;
import persistence.XMLAccessor;

import java.io.IOException;

public class OpenPresentationCommand implements Command {
    private Presentation presentation;
    private String filename;

    public OpenPresentationCommand(Presentation presentation, String filename) {
        this.presentation = presentation;
        this.filename = filename;
    }

    @Override
    public void execute() throws IOException {
        XMLAccessor accessor = new XMLAccessor();
        accessor.loadFile(presentation, filename);
    }
}
