package lab2;

import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import lab2.Beer.Beer;
import lab2.Parser.ParserDOM;
import lab2.Parser.ParserSAX;
import lab2.Parser.ParserStAX;
import lab2.Utils.Utils;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        Boolean xml = Utils.validateXMLSchema("lab2/resources/Beer.xsd", "lab2/resources/Beer.xml");
        ParserDOM pd = new ParserDOM();
        ParserSAX ps = new ParserSAX();
        ParserStAX pts = new ParserStAX();

        List<Beer> lb1 = pd.parse("lab2/resources/Beer.xml");
        List<Beer> lb2 = ps.parse("lab2/resources/Beer.xml");
        List<Beer> lb3 = pts.parse("lab2/resources/Beer.xml");

        System.out.println(xml);
    }
}
