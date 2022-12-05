package lab1.taxidepot;

public class DieselCar extends Car{
    public DieselCar(int price, int year, int mileage, int maxSpeed, String brand, int seatsCount,  Boolean automaticTransmission, Boolean airConditioner, int consuption) 
    {
        super(price, year, mileage, maxSpeed, brand, "DIESEL", seatsCount, automaticTransmission, airConditioner, consuption);
    }    
}
