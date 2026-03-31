package model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Slide
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private String title;
    private final List<SlideItem> items = new ArrayList<>();

    public void append(SlideItem anItem) {
        items.add(anItem);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void append(int level, String message) {
        append(new TextItem(level, message));
    }

    public SlideItem getSlideItem(int number) {
        return items.get(number);
    }

    public List<SlideItem> getSlideItems() {
        return java.util.Collections.unmodifiableList(items);
    }

    public int getSize() {
        return items.size();
    }


    public void draw(Graphics g, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;
        SlideItem slideItem = new TextItem(0, getTitle());
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, g, style, view);
        y += slideItem.getBoundingBox(g, view, scale, style).height;
        for (SlideItem item : items) {
            style = Style.getStyle(item.getLevel());
            item.draw(area.x, y, scale, g, style, view);
            y += item.getBoundingBox(g, view, scale, style).height;
        }
    }

    private float getScale(Rectangle area) {
        return Math.min(((float) area.width) / WIDTH, ((float) area.height) / HEIGHT);
    }
}
