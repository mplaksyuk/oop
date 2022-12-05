package lab1.taxidepot;

import java.util.*;

public class Depot {
    public static List<Car> CarList;

    public Depot (List<Car> carList)
    {
        Depot.CarList = carList;
    }

    public List<Car> getCarList() {
        return CarList;
    }

    public long CalculateCarsPrice()
    {
        long sum = 0;

        for (Car car : CarList) { sum += car.Price; }

        return sum;
    }

    public List<Car> SearchBySpeed (int min, int max)
    {
        List<Car> res = new ArrayList<>();

        for (Car car : CarList) { 
            if (car.MaxSpeed >= min && car.MaxSpeed <= max)
                res.add(car);
        }
        
        for(Car car : res) {
            System.out.println(car.toString());
        }

        return res;
    }

    public void ShowCars() {
        for (Car car : CarList) {
            System.out.println(car.toString());
        }
    }

    public void addCar(Car car) {
        CarList.add(car);
    }

    public void deleteCar(int index) {
        CarList.remove(index - 1);
    }

    public void SortByFueldAndConsuption() {
        
        List<Car> Gas     = new ArrayList<>();
        List<Car> Diesel  = new ArrayList<>();
        List<Car> Electro = new ArrayList<>();
        List<Car> Petrol  = new ArrayList<>();
        

        for (Car car : CarList) {
            if (car.Fuel == "GAS") {
                Gas.add(car);
                continue;
            }
            else if (car.Fuel == "DIESEL") {
                Diesel.add(car);
                continue;
            }
            else if (car.Fuel == "ELECTRO") {
                Electro.add(car);
                continue;
            }
            Petrol.add(car);
        }

        Gas.sort(Comparator.comparing(Car::getConsuption));
        Diesel.sort(Comparator.comparing(Car::getConsuption));
        Electro.sort(Comparator.comparing(Car::getConsuption));
        Petrol.sort(Comparator.comparing(Car::getConsuption));

        List<Car> result = new ArrayList<Car>();
        result.addAll(Gas);
        result.addAll(Diesel);
        result.addAll(Electro);
        result.addAll(Petrol);

        CarList = result;
    }
}
