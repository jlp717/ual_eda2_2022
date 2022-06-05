package practica_4;

public class AristaGenerador {
	
	private int origen;
	private int destino;
	private int peso;
	
	public AristaGenerador () {
	}
	
	public AristaGenerador (int origen, int destino, int peso) {
		this.origen = origen;
		this.destino = destino;
		this.peso = peso;
	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public int getDestino() {
		return destino;
	}

	public void setDestino(int destino) {
		this.destino = destino;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return origen + " " + destino + " " + peso;
	}


}
