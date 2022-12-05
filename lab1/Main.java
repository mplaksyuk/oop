package lab1;

import java.util.*;

import lab1.taxidepot.Car;
import lab1.taxidepot.Depot;
import lab1.taxidepot.DieselCar;
import lab1.taxidepot.ElectroCar;
import lab1.taxidepot.GasCar;
import lab1.taxidepot.PetrolCar;

public class Main {
    public static void main(String[] args) {

        List<Car> CarList = new ArrayList<>();

        GasCar g1 = new GasCar(1000, 1989, 45000, 290, "TOYOTA", 1, true, false, 9);
        GasCar g2 = new GasCar(1231, 2000, 672527, 300, "BMW", 6, true, true, 25);
        ElectroCar e1 = new ElectroCar(845, 1993, 87978, 80, "TOYOTA", 3, true, false, 7);
        ElectroCar e2 = new ElectroCar(767, 2002, 67263, 160, "AUDI", 2, true, true, 10);
        DieselCar d1 = new DieselCar(1500, 2000, 76773, 350, "AUDI", 5, false, true, 13);
        DieselCar d2 = new DieselCar(6763, 1988, 87633, 220, "TOYOTA", 5, false, true, 12);
        PetrolCar p1 = new PetrolCar(4142, 2022, 165276, 212, "VOLKSWAGEN GOLF", 2, true, false, 5);
        PetrolCar p2 = new PetrolCar(1938, 1999, 6484, 120, "BMW", 4, true, false, 20);
        
        CarList.add(g2);
        CarList.add(e2);
        CarList.add(g1);
        CarList.add(e1);
        CarList.add(d2);
        CarList.add(p1);
        CarList.add(d1);
        CarList.add(p2);

        Depot depot = new Depot(CarList);

        while(true) {
            System.out.println("1 View list");
            System.out.println("2 Add");
            System.out.println("3 Delete by index");
            System.out.println("4 Sort by Fuel type and Consuption");
            System.out.println("5 Find range");
            System.out.println("6 Total Price");
            System.out.println("7 Exit");
            getChoiceMenu(CarList, depot);

        }

    }

    public static void getChoiceMenu(List<Car> CarList, Depot depot) {
        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine();

        switch (userChoice) {
            case "1"://show
                depot.ShowCars();
                break;
            case "2"://add
                System.out.println("Price: ");
                int Price = scanner.nextInt();
                System.out.println("Year: ");
                int Year = scanner.nextInt(); 
                System.out.println("Mileage: ");
                int Mileage = scanner.nextInt(); 
                System.out.println("MaxSpeed: ");
                int MaxSpeed = scanner.nextInt();
                System.out.println("Brand: ");
                String Brand = scanner.nextLine();
                System.out.println("SeatsCount: ");
                int SeatsCount = scanner.nextInt();
                System.out.println("Fuel: (GAS/ELECTRO/PETROL/DIESEL)");
                String Fuel = scanner.nextLine();
                System.out.println("AutomaticTransmission: true / false");
                Boolean AutomaticTransmission = scanner.nextBoolean();;
                System.out.println("AirConditioner: true / false");
                Boolean AirConditioner = scanner.nextBoolean();;
                System.out.println("Consuption: ");
                int Consuption = scanner.nextInt();

                switch(Fuel) {
                    case "GAS":
                        GasCar gcar = new GasCar(Price, Year, Mileage, MaxSpeed, Brand, SeatsCount, AutomaticTransmission, AirConditioner, Consuption);
                        depot.addCar(gcar);
                        break;
                    case "PETROL":
                        ElectroCar ecar = new ElectroCar(Price, Year, Mileage, MaxSpeed, Brand, SeatsCount, AutomaticTransmission, AirConditioner, Consuption);
                        depot.addCar(ecar);
                        break;
                    case "ELECTRO":
                        PetrolCar pcar = new PetrolCar(Price, Year, Mileage, MaxSpeed, Brand, SeatsCount, AutomaticTransmission, AirConditioner, Consuption);
                        depot.addCar(pcar);
                        break;
                    case "DIESEL":
                        DieselCar dcar = new DieselCar(Price, Year, Mileage, MaxSpeed, Brand, SeatsCount, AutomaticTransmission, AirConditioner, Consuption);
                        depot.addCar(dcar);
                        break;
                    default: {
                        System.out.println("FUEL ERROR");
                        break;
                    }
                }
                
                break;
            case "3"://delete
                System.out.println("Index from 1:");
                int index = scanner.nextInt();
                depot.deleteCar(index);
                break;

            case "4"://sort
                depot.SortByFueldAndConsuption();
                break;

            case "5"://find
                System.out.println("min:");
                int min=scanner.nextInt();
                System.out.println("max:");
                int max=scanner.nextInt();
                depot.SearchBySpeed(min, max);
                break;

            case "6"://total price
                long totalprice = depot.CalculateCarsPrice();
                System.out.println("Total price: " + totalprice);
                break;

            case "7"://exit
                System.out.println("The program is closed.");
                System.exit(0);
                break;

            default:
                break;
        }
    }
}