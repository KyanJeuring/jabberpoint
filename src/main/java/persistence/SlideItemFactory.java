package persistence;

import model.BitmapItem;
import model.SlideItem;
import model.TextItem;

import java.io.IOException;

public class SlideItemFactory {
    public static SlideItem createSlideItem(String type, int level, String content) throws IOException
    {
        if ("text".equals(type)) {
            return new TextItem(level, content);
        } else if ("image".equals(type)) {
            return new BitmapItem(level, content);
        }
        return null;
    }
}
