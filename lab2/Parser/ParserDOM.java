package lab2.Parser;

import lab2.Beer.*;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lab2.Parser.Tag.*;

public class ParserDOM {
    DocumentBuilderFactory dbf;

    private static Document buildDocument(String src) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(src);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(file);
    }

    public static ArrayList<Beer> parse(String src) {
        Document document;
        ArrayList<Beer> beerMarket = new ArrayList<>();
        try {
            document = buildDocument(src);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Node rootNode = document.getFirstChild();
        NodeList beers = rootNode.getChildNodes();
        for (int i = 0; i < beers.getLength(); i++) {

            BeerChars beerChras = null;
            Bottle bottle = null;

            // BeerType beerType = null;
            List<Ingredients> ingredientsArr = new ArrayList<>();


            if (beers.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            NamedNodeMap beersAttrs = beers.item(i).getAttributes();

            String name = beersAttrs.getNamedItem(BEER_NAME.getTag()).getNodeValue();
            BeerType type = BeerType.valueOf(beersAttrs.getNamedItem(BEER_TYPE.getTag()).getNodeValue());
            Boolean alco = Boolean.parseBoolean(beersAttrs.getNamedItem(BEER_ALCO.getTag()).getNodeValue());
            String manufacturer = beersAttrs.getNamedItem(BEER_MANUFACTURER.getTag()).getNodeValue();

            //Take Analogs and Versions
            NodeList beer_elements = beers.item(i).getChildNodes();
            for (int j = 0; j < beer_elements.getLength(); j++) {
                if (beer_elements.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                //check Analogs
                if (beer_elements.item(j).getNodeName() == INGREDIENTS.getTag()) {
                    NodeList ingredients = beer_elements.item(j).getChildNodes();
                    for (int k = 0; k < ingredients.getLength(); k++) {
                        if (ingredients.item(k).getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }
                        ingredientsArr.add(Ingredients.valueOf(ingredients.item(k).getTextContent()));
                    }
                }
                else if (beer_elements.item(j).getNodeName() == BEERCHARS.getTag()) {
                    NamedNodeMap beerCharsAttrs = beer_elements.item(j).getAttributes();

                    double alcoPercentage = Double.parseDouble(beerCharsAttrs.getNamedItem(BEERCHARS_ALCOPERCENTAGE.getTag()).getNodeValue());
                    double opacity = Double.parseDouble(beerCharsAttrs.getNamedItem(BEERCHARS_OPACITY.getTag()).getNodeValue());
                    Boolean filtered = Boolean.parseBoolean(beerCharsAttrs.getNamedItem(BEERCHARS_FILTERED.getTag()).getNodeValue());
                    double NutritionalValue = Double.parseDouble(beerCharsAttrs.getNamedItem(BEERCHARS_NUTRITIONALVALUE.getTag()).getNodeValue());
                    
                    beerChras = new BeerChars(alcoPercentage, opacity, filtered, NutritionalValue);
                }

                else if (beer_elements.item(j).getNodeName() == BOTTLE.getTag()) {
                    NamedNodeMap bottlwAttrs = beer_elements.item(j).getAttributes();

                    double capacity = Double.parseDouble(bottlwAttrs.getNamedItem(BOTTLE_CAPACITY.getTag()).getNodeValue());
                    BottleMaterials material = BottleMaterials.valueOf(bottlwAttrs.getNamedItem(BOTTLE_MATERIAL.getTag()).getNodeValue());

                    bottle = new Bottle(capacity, material);
                }
            }

            beerMarket.add(new Beer(name, type, alco, manufacturer, ingredientsArr, beerChras, bottle));
        }

        return beerMarket;
    }

}