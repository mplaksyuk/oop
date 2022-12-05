package lab1.taxidepot;

public class ElectroCar extends Car {
    public ElectroCar(int price, int year, int mileage, int maxSpeed, String brand, int seatsCount,  Boolean automaticTransmission, Boolean airConditioner, int consuption) 
    {
        super(price, year, mileage, maxSpeed, brand, "ELECTRO", seatsCount, automaticTransmission, airConditioner, consuption);
    }
}
