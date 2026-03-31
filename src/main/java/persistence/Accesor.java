package persistence;

import model.Presentation;
import java.io.IOException;

public interface Accesor {
    void loadFile(Presentation presentation, String filename) throws IOException;
    void saveFile(Presentation presentation, String filename) throws IOException;
}
