package lab2.Beer;

import java.util.Objects;

public class Bottle {
    public double Capacity;
    public BottleMaterials Material;
    
    public Bottle(double capacity, BottleMaterials material) {
        this.Capacity = capacity;
        this.Material = material;
    }

    public Boolean equel(Bottle b) {
        if (this.Capacity == b.Capacity && this.Material == b.Material)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Capacity, Material);
    }
}
