package practica_2_codigo;

public class Arista implements Comparable<Arista>{
	
	private String origen;
	private String destino;
	private double peso;
	
	public Arista () {
	}
	
	public Arista (String origen, String destino, double peso) {
		this.origen = origen;
		this.destino = destino;
		this.peso = peso;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return origen + " --> " + destino + "| peso=" + peso + " ";
	}

	@Override
	public int compareTo(Arista other) {
		int compare = Double.compare(this.getPeso(), other.getPeso());
		return compare == 0 ? this.getOrigen().compareTo(other.getDestino()) : compare;
	}

}
