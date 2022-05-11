package practica_3;

public class Objeto {
	
    private String name;
    private double weight;
    private double profit;
    private double quantity;
    
    public Objeto(String name, double weight, double profit) {
        this.name = name;
        this.weight = weight;
        this.profit = profit;
        this.quantity = 1;
    }

    public String getName() {
        return name;
    }

    public Double getPeso() {
        return weight;
    }

    public Double getBeneficio() {
        return profit;
    }

    public void setBeneficio(double profit) {
        this.profit = profit;
    }
    
    public double getCantidad() {
        return quantity;
    }

    public void setCantidad(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ", weight=" + (weight*quantity) + ", profit=" + (profit*quantity);
    }
    
}
