package persistence;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLAccessor implements Accesor {

    private static final String SHOW_TITLE = "showtitle";
    private static final String SLIDE_TITLE = "title";
    private static final String SLIDE = "slide";
    private static final String ITEM = "item";
    private static final String LEVEL = "level";
    private static final String KIND = "kind";

    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        if (titles.getLength() == 0) {
            return "";
        }
        return titles.item(0).getTextContent();
    }

    private String escapeXML(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }

    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, SHOW_TITLE));

            NodeList slides = doc.getElementsByTagName(SLIDE);
            for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, SLIDE_TITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML file", e);
        }
    }

    protected void loadSlideItem(Slide slide, Element item) throws IOException
    {
        int level = 1;
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
        if (leveltext != null) {
            try {
                level = Integer.parseInt(leveltext);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid level format", e);
            }
        }
        String type = attributes.getNamedItem(KIND).getTextContent();
        String content = item.getTextContent();
        SlideItem slideItem = SlideItemFactory.createSlideItem(type, level, content);
        if (slideItem != null) {
            slide.append(slideItem);
        } else {
            throw new IllegalArgumentException("Unknown slide item type: " + type);
        }
    }

    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
            out.println("<presentation>");
            out.print("<showtitle>");
            out.print(escapeXML(presentation.getTitle()));
            out.println("</showtitle>");
            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
                Slide slide = presentation.getSlide(slideNumber);
                out.println("<slide>");
                out.println("<title>" + escapeXML(slide.getTitle()) + "</title>");
                List<SlideItem> slideItems = slide.getSlideItems();
                for (SlideItem slideItem : slideItems) {
                    out.print("<item kind=");
                    if (slideItem instanceof TextItem) {
                        out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
                        out.print(escapeXML(((TextItem) slideItem).getText()));
                    } else if (slideItem instanceof BitmapItem) {
                        out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                        out.print(escapeXML(((BitmapItem) slideItem).getName()));
                    }
                    out.println("</item>");
                }
                out.println("</slide>");
            }
            out.println("</presentation>");
        }
    }
}
