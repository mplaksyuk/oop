package lab2.Parser;

public enum Tag {
    BEERMARKET("BeerMarket"),
    
    BEER("Beer"),
    BEER_NAME("Name"),
    BEER_TYPE("Type"),
    BEER_ALCO("Alco"),
    BEER_MANUFACTURER("Manufacturer"),

    INGREDIENTS("Ingredients"),
    INGREDIENT("Ingredient"),

    BEERCHARS("BeerChars"),
    BEERCHARS_ALCOPERCENTAGE("AlcoPercentage"),
    BEERCHARS_OPACITY("Opacity"),
    BEERCHARS_FILTERED("Filtered"),
    BEERCHARS_NUTRITIONALVALUE("NutritionalValue"),

    BOTTLE("Bottle"),
    BOTTLE_CAPACITY("Capacity"),
    BOTTLE_MATERIAL("Material"),
    ;
    private String tag;

    Tag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }


    public String getTag() {
        return tag;
    }
}