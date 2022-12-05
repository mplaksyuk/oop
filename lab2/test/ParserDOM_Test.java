package lab2.test;

import org.junit.Test;

import lab2.Beer.*;
import org.junit.Assert;
import org.junit.Test;
import lab2.Parser.ParserDOM;
import lab2.Utils.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static lab2.Utils.Utils.sortByName;

public class ParserDOM_Test {
    private static final String src = "/Users/mplaksyuk/src/oop/lab2/resources/Beer.xml";

    @Test
    public void BeerAttrTest(){
        ParserDOM parserDom = new ParserDOM();
        ArrayList<Beer> beerMarket = parserDom.parse(src);

        Assert.assertEquals("Biba", beerMarket.get(0).getName());
        Assert.assertEquals(BeerType.valueOf("Dark"), beerMarket.get(0).getBeerType());
        Assert.assertEquals(true, beerMarket.get(0).getAlco());
        Assert.assertEquals("Bad", beerMarket.get(0).getManufacturer());

        Assert.assertEquals("Boba", beerMarket.get(1).getName());
        Assert.assertEquals(BeerType.valueOf("Lager"), beerMarket.get(1).getBeerType());
        Assert.assertEquals(true, beerMarket.get(1).getAlco());
        Assert.assertEquals("IPA", beerMarket.get(1).getManufacturer());

        Assert.assertEquals("Baba", beerMarket.get(2).getName());
        Assert.assertEquals(BeerType.valueOf("Light"), beerMarket.get(2).getBeerType());
        Assert.assertEquals(false, beerMarket.get(2).getAlco());
        Assert.assertEquals("Chernigivke", beerMarket.get(2).getManufacturer());

    }
    @Test
    public void BeerIngredientsTest(){
        ParserDOM parserDom = new ParserDOM();
        ArrayList<Beer> beerMarket = parserDom.parse(src);

        ArrayList<Ingredients> ingredients1 = new ArrayList<>(Arrays.asList(Ingredients.valueOf("Water"), Ingredients.valueOf("Hop"), Ingredients.valueOf("Sugar")));
        ArrayList<Ingredients> ingredients2 = new ArrayList<>(Arrays.asList(Ingredients.valueOf("Water"), Ingredients.valueOf("Hop"), Ingredients.valueOf("Malt"), Ingredients.valueOf("Yeast")));
        ArrayList<Ingredients> ingredients3 = new ArrayList<>(Arrays.asList(Ingredients.valueOf("Water"), Ingredients.valueOf("Hop"), Ingredients.valueOf("Sugar"), Ingredients.valueOf("Yeast"), Ingredients.valueOf("Malt")));

        Assert.assertEquals(ingredients1, beerMarket.get(0).getIngredients());
        Assert.assertEquals(ingredients2, beerMarket.get(1).getIngredients());
        Assert.assertEquals(ingredients3, beerMarket.get(2).getIngredients());
    }
    @Test
    public void BeerCharsTest(){
        ParserDOM parserDom = new ParserDOM();
        ArrayList<Beer> beerMarket = parserDom.parse(src);

        BeerChars beerChars1 = new BeerChars(4.3, 0.3, false, 125);
        BeerChars beerChars2 = new BeerChars(0, 1, true, 200);
        BeerChars beerChars3 = new BeerChars(8.2, 0.5, true, 346);

        Assert.assertTrue(beerChars1.equel(beerMarket.get(0).getBeerChars()));
        Assert.assertTrue(beerChars2.equel(beerMarket.get(1).getBeerChars()));
        Assert.assertTrue(beerChars3.equel(beerMarket.get(2).getBeerChars()));
    }
    @Test
    public void BeerBottleTest(){
        ParserDOM parserDom = new ParserDOM();
        ArrayList<Beer> beerMarket = parserDom.parse(src);

        Bottle bottle1 = new Bottle(0.5, BottleMaterials.valueOf("Steal"));
        Bottle bottle2 = new Bottle(1, BottleMaterials.valueOf("Glass"));
        Bottle bottle3 = new Bottle(0.5, BottleMaterials.valueOf("Steal"));

        Assert.assertTrue(bottle1.equel(beerMarket.get(0).getBottle()));
        Assert.assertTrue(bottle2.equel(beerMarket.get(1).getBottle()));
        Assert.assertTrue(bottle3.equel(beerMarket.get(2).getBottle()));
    }
}