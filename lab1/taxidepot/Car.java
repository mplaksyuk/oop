package lab1.taxidepot;

abstract public class Car {
    public int Price;
    public int Year;
    public int Mileage;
    public int MaxSpeed;
    public String Brand;
    public int SeatsCount;
    public String Fuel;
    public Boolean AutomaticTransmission;
    public Boolean AirConditioner;
    public int Consuption;


    public Car(int price, int year, int mileage, int maxSpeed, String brand, String fuel,  int seatsCount, Boolean automaticTransmission, Boolean airConditionerg, int consuption)
    {
        this.Price = price;
        this.Year = year;
        this.Mileage = mileage;
        this.MaxSpeed = maxSpeed;
        this.SeatsCount = seatsCount;
        this.Fuel = fuel;
        this.AutomaticTransmission = automaticTransmission;
        this.AirConditioner = airConditionerg;
        this.Consuption = consuption;
    }

    public int getConsuption() {
        return Consuption;
    }

    @Override
    public String toString()
    {
        return "Price: " + Price + " Year: " + Year + " Mileage: " + 
        Mileage + " Max Speed: " + MaxSpeed + " Seats Count: " + 
        SeatsCount + " Fuel Type: : " + Fuel + " Automatic Transmission: " + 
        AutomaticTransmission + " Air Conditioner: " + AirConditioner + " Consuption: " + Consuption;
    }

}
