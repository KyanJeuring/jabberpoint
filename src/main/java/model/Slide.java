package model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Slide
{
    private int width;
    private int height;
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
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        if(width > 300 && width < 1500)
        {
            this.width = width;
        }
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        if(height > 300 && height < 1500)
        {
            this.height = height;
        }
    }


    public void append(int level, String message) {
        try {
            append(new TextItem(level, message));
        } catch (IOException e) {
            // This should not happen with TextItem
            e.printStackTrace();
        }
    }

    public SlideItem getSlideItem(int number) {
        return items.get(number);
    }

    public List<SlideItem> getSlideItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }


    public void draw(Graphics g, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;
        try {
            SlideItem slideItem = new TextItem(0, getTitle());
            Style style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
            for (SlideItem item : items) {
                style = Style.getStyle(item.getLevel());
                item.draw(area.x, y, scale, g, style, view);
                y += item.getBoundingBox(g, view, scale, style).height;
            }
        } catch (IOException e) {
            // This should not happen with TextItem
            e.printStackTrace();
        }
    }

    private float getScale(Rectangle area) {
        return Math.min(((float) area.width) / 1200, ((float) area.height) / 800);
    }
}
