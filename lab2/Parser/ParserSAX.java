package lab2.Parser;

import lab2.Beer.*;
import org.w3c.dom.Document;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;

public class ParserSAX {
    public static ArrayList<Beer> parse(String src) {
        Document document;
        ArrayList<Beer> beerMarket = new ArrayList<>();
        try {
            File file = new File(src);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParserHandler handler = new SAXParserHandler();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, handler);
            beerMarket = handler.getBeerMarket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return beerMarket;
    }
}