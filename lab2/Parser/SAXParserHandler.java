package lab2.Parser;

import lab2.Beer.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import static lab2.Parser.Tag.*;

public class SAXParserHandler extends DefaultHandler {
    private String currentTagName;

    private ArrayList<Beer> beerMarket = null;
    private Beer beer = null;
    private ArrayList<Ingredients> ingredientsArr = null;
    private BeerChars beerChars = null;
    private Bottle bottle = null;

    @Override
    public void startDocument() throws SAXException {
        beerMarket = new ArrayList<>();

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(BEER.getTag())){
            beer = new Beer();
            beer.setName(attributes.getValue(BEER_NAME.getTag()));
            beer.setBeerType(BeerType.valueOf(attributes.getValue(BEER_TYPE.getTag())));
            beer.setAlco(Boolean.parseBoolean(attributes.getValue(BEER_ALCO.getTag())));
            beer.setManufacturer(attributes.getValue(BEER_MANUFACTURER.getTag()));
            
        } 
        else if (qName.equalsIgnoreCase(INGREDIENTS.getTag())){
            ingredientsArr = new ArrayList<>();
        }
        else if (qName.equalsIgnoreCase(BEERCHARS.getTag())){
            double alcoPercentage = Double.parseDouble(attributes.getValue(BEERCHARS_ALCOPERCENTAGE.getTag()));
            double opacity = Double.parseDouble(attributes.getValue(BEERCHARS_OPACITY.getTag()));
            Boolean filtered = Boolean.parseBoolean(attributes.getValue(BEERCHARS_FILTERED.getTag()));
            double nutritionalValue = Double.parseDouble(attributes.getValue(BEERCHARS_NUTRITIONALVALUE.getTag()));

            beerChars = new BeerChars(alcoPercentage, opacity, filtered, nutritionalValue);

            beer.setBeerChars(beerChars);
        }else if (qName.equalsIgnoreCase(BOTTLE.getTag())){
            double capacity = Double.parseDouble(attributes.getValue(BOTTLE_CAPACITY.getTag()));
            BottleMaterials material = BottleMaterials.valueOf(attributes.getValue(BOTTLE_MATERIAL.getTag()));

            bottle = new Bottle(capacity, material);

            beer.setBottle(bottle);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(INGREDIENT.getTag())){
            String ingredient = currentTagName;
            ingredientsArr.add(Ingredients.valueOf(ingredient));
        }else if (qName.equalsIgnoreCase(INGREDIENTS.getTag())){
            beer.setIngredients(ingredientsArr);
        }else if (qName.equalsIgnoreCase(BEER.getTag())){
            beerMarket.add(beer);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentTagName = new String(ch, start, length);
    }

    public ArrayList<Beer> getBeerMarket() {
        return beerMarket;
    }
}