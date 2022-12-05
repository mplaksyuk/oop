package lab2.Parser;

import lab2.Beer.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static lab2.Parser.Tag.*;

public class ParserStAX {
    public ArrayList<Beer> parse(String src) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        File file = new File(src);
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));

        ArrayList<Beer> beerMarket = new ArrayList<>();
        Beer beer = null;
        ArrayList<Ingredients> ingredientsArr = null;
        BeerChars beerChars = null;
        Bottle bottle = null;

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals(BEER.getTag())) {
                    beer = new Beer();
                    Attribute name = startElement.getAttributeByName(new QName(BEER_NAME.getTag()));
                    Attribute beerType = startElement.getAttributeByName(new QName(BEER_TYPE.getTag()));
                    Attribute alco = startElement.getAttributeByName(new QName(BEER_ALCO.getTag()));
                    Attribute manufacturer = startElement.getAttributeByName(new QName(BEER_MANUFACTURER.getTag()));

                    if (name != null)         beer.setName(name.getValue());
                    if (beerType != null)     beer.setBeerType(BeerType.valueOf(beerType.getValue()));
                    if (alco != null)         beer.setAlco(Boolean.parseBoolean(alco.getValue()));
                    if (manufacturer != null) beer.setManufacturer((manufacturer.getValue()));

                } else if (startElement.getName().getLocalPart().equals(INGREDIENTS.getTag())) {
                    ingredientsArr = new ArrayList<>();
                } else if (startElement.getName().getLocalPart().equals(INGREDIENT.getTag())){
                    nextEvent = reader.nextEvent();
                    ingredientsArr.add(Ingredients.valueOf(nextEvent.asCharacters().getData()));
                } else if (startElement.getName().getLocalPart().equals(BEERCHARS.getTag())){
                    Attribute alcoPercentage = startElement.getAttributeByName(new QName(BEERCHARS_ALCOPERCENTAGE.getTag()));
                    Attribute opacity = startElement.getAttributeByName(new QName(BEERCHARS_OPACITY.getTag()));
                    Attribute filtered = startElement.getAttributeByName(new QName(BEERCHARS_FILTERED.getTag()));
                    Attribute nutritionalValue = startElement.getAttributeByName(new QName(BEERCHARS_NUTRITIONALVALUE.getTag()));

                    if (alcoPercentage != null && opacity != null && filtered != null && nutritionalValue != null) {

                        beerChars = new BeerChars(Double.parseDouble(alcoPercentage.getValue()),
                            Double.parseDouble(opacity.getValue()),
                            Boolean.parseBoolean(opacity.getValue()),
                            Double.parseDouble(nutritionalValue.getValue()));
                    }
                }else if (startElement.getName().getLocalPart().equals(BOTTLE.getTag())){
                    Attribute capacity = startElement.getAttributeByName(new QName(BOTTLE_CAPACITY.getTag()));
                    Attribute material = startElement.getAttributeByName(new QName(BOTTLE_MATERIAL.getTag()));

                    if (capacity != null && material != null) {

                        bottle = new Bottle(Double.parseDouble(capacity.getValue()), BottleMaterials.valueOf(material.getValue()));
                    }
                }
            }
            if (nextEvent.isEndElement()){
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals(BEER.getTag())){
                    beer.setIngredients(ingredientsArr);
                    beer.setBeerChars(beerChars);
                    beer.setBottle(bottle);

                    beerMarket.add(beer);
                }
            }
        }

        return beerMarket;
    }
}