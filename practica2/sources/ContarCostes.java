package practica_2_codigo;

import java.util.List;

public class ContarCostes {

	private double costeTotal = 0.0;
	
	/*
	 * Sencillamente recorremos la lista correspondiente sumando los pesos resultantes.
	 */
	
	public ContarCostes (List<Arista> lista) {
		for (int i = 0; i < lista.size(); i++) {
			  costeTotal = costeTotal + lista.get(i).getPeso();
			}
	}

	public double getPrecioTotal() {
		return costeTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.costeTotal = precioTotal;
	}
	
	public String toString() {
		return "\nCoste Total --> " + costeTotal;
	}
}
