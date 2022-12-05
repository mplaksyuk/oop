package lab2.Beer;
import java.util.*;

public class Beer {
    public String Name;
    public BeerType Type;
    public Boolean Alco;
    public String Manufacturer;
    public List<Ingredients> Ingredients;

    //characteristics
    public BeerChars Characteristics;

    public Bottle Bottle;

    public Beer() {};

    public Beer(String name, BeerType type, Boolean alco, String manufacturer, List<Ingredients> ingredients, BeerChars characteristics, Bottle bottle) {
        this.Name = name;
        this.Type = type;
        this.Alco = alco;
        this.Manufacturer = manufacturer;
        this.Ingredients = ingredients;
        this.Characteristics = characteristics;
        this.Bottle = bottle;
    }

    public int compare(Beer anotherBeer){
        return this.Name.compareTo(anotherBeer.Name);
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }

    public void setBeerType(BeerType type) {
        this.Type = type;
    }

    public BeerType getBeerType() {
        return this.Type;
    }

    public void setAlco(Boolean alco) {
        this.Alco = alco;
    }

    public Boolean getAlco() {
        return this.Alco;
    }

    public void setManufacturer(String manufacturer) {
        this.Manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return this.Manufacturer;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.Ingredients = ingredients;
    }

    public List<Ingredients> getIngredients() {
        return this.Ingredients;
    }

    public void setBeerChars(BeerChars chars) {
        this.Characteristics = chars;
    }

    public BeerChars getBeerChars() {
        return this.Characteristics;
    }

    public void setBottle(Bottle bottle) {
        this.Bottle = bottle;
    }

    public Bottle getBottle() {
        return this.Bottle;
    }

}
