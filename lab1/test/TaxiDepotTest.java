package lab1.test;


import lab1.taxidepot.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TaxiDepotTest {
    @Test
    public void TaxiSearchBySpeed_Test(){
        List<Car> CarList = new ArrayList<>();

        List<Car> CarList2 = new ArrayList<>();

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

        CarList2.add(e2);
        CarList2.add(e1);
        CarList2.add(p2);

        Depot depot2 = new Depot(CarList2);

        Assert.assertEquals(depot.SearchBySpeed(50, 200), depot2.getCarList());
    }

    @Test
    public void TaxiSortByConsuption_Test(){
        List<Car> CarList = new ArrayList<>();

        List<Car> CarList2 = new ArrayList<>();

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

        CarList2.add(g2);
        CarList2.add(g1);
        CarList2.add(d2);
        CarList2.add(d1);
        CarList2.add(e2);
        CarList2.add(e1);
        CarList2.add(p2);
        CarList2.add(p1);

        Depot depot2 = new Depot(CarList2);

        depot.SortByFueldAndConsuption();

        Assert.assertEquals(depot.getCarList(), depot2.getCarList());
    }
}