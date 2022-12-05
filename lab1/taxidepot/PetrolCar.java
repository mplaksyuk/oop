package lab1.taxidepot;

public class PetrolCar extends Car {
    public PetrolCar(int price, int year, int mileage, int maxSpeed, String brand, int seatsCount,  Boolean automaticTransmission, Boolean airConditioner, int consuption) 
    {
        super(price, year, mileage, maxSpeed, brand, "PETROL", seatsCount, automaticTransmission, airConditioner, consuption);
    }
}
