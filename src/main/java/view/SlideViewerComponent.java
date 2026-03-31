package view;

import model.Presentation;
import model.Slide;
import model.Observer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class SlideViewerComponent extends JComponent implements Observer {

    private Slide slide;
    private Font labelFont = null;
    private Presentation presentation = null;
    private JFrame frame = null;

    private static final long serialVersionUID = 227L;

    private static final Color BG_COLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONT_NAME = "Dialog";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int FONT_HEIGHT = 10;
    private static final int X_POS = 1100;
    private static final int Y_POS = 20;

    public SlideViewerComponent(Presentation pres, JFrame frame) {
        setBackground(BG_COLOR);
        this.presentation = pres;
        this.labelFont = new Font(FONT_NAME, FONT_STYLE, FONT_HEIGHT);
        this.frame = frame;
        this.presentation.addObserver(this);
    }

    @Override
    public void update() {
        this.slide = presentation.getCurrentSlide();
        frame.setTitle(presentation.getTitle());
        repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (presentation.getSlideNumber() < 0 || slide == null) {
            return;
        }
        g.setFont(labelFont);
        g.setColor(COLOR);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                 presentation.getSize(), X_POS, Y_POS);
        Rectangle area = new Rectangle(0, Y_POS, getWidth(), (getHeight() - Y_POS));
        slide.draw(g, area, this);
    }
}
