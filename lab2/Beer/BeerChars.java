package lab2.Beer;

import java.util.Objects;

public class BeerChars {
    public double AlcoPercentage;
    public double Opacity;
    public Boolean Filtered;
    public double NutritionalValue;

    public BeerChars(double alcoPercentage, double opacity, Boolean filtered, double nutritionalValue) {
        this.AlcoPercentage =  alcoPercentage;
        this.Opacity =  opacity;
        this.Filtered =  filtered;
        this.NutritionalValue =  nutritionalValue;
    }

    public Boolean equel(BeerChars bc) {
        if (this.AlcoPercentage == bc.AlcoPercentage && this.Opacity == bc.Opacity && Filtered == this.Filtered && this.NutritionalValue == bc.NutritionalValue)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(AlcoPercentage, Opacity, Filtered, NutritionalValue);
    }
}
