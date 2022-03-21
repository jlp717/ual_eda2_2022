package practica_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Scanner;

public class DyVSolution {
	
	private static ArrayList<Player> estadisticas;
	
	public static double calcularMedia(long[] tiempos) {
		double valorMedio = 0.0;
		
		for (int i = 0; i < tiempos.length - 1; i++) {
			valorMedio += tiempos[i];
		}
		return valorMedio / (tiempos.length - 1);
	}
	
	public static void main(String [] args) {
		cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		
		//Hacemos el calculo de tiempos de 10 elementos para sacar un tiempo medio
		//El tiempo no no contemplamos en la carga de datos, solo en los metodos
		
		long tiempoInicio;
		long tiempoFinal;
		long tiempoResultante;
		long[] tiempos = new long[10]; 
		
		for (int i = 0; i < 10; i++) {
			tiempoInicio = System.currentTimeMillis();
			diezMejoresJugadores();
			tiempoFinal = System.currentTimeMillis();
			tiempoResultante = tiempoFinal - tiempoInicio;
			tiempos[i] = tiempoResultante;
		}
		
		double valorTiempoMedio = calcularMedia(tiempos);
		System.out.printf("\nTiempo Medio = %.4f milisegundos\n", valorTiempoMedio);
		
	}
	
	public static ArrayList<Player> diezMejoresJugadores() {
		ArrayList<Player> arrayADevolver = diezMejoresJugadores(0,estadisticas.size()-1,estadisticas);
		System.out.println("\nLos diez mejores jugadores de toda la historia son:\n");
		//System.out.println(arrayADevolver.size());
		//Collections.reverse(arrayADevolver);
		for (int i = arrayADevolver.size()-10; i < arrayADevolver.size(); i++) {
			System.out.println(arrayADevolver.get(i));
		}
		return arrayADevolver;
	}
	
	public static ArrayList<Player> combinarArray(ArrayList<Player> izquierda, ArrayList<Player> derecha) {
		int indiceIzq = 0;
		int indiceDer = 0;
		
		ArrayList<Player> arrayOrdenado = new ArrayList<Player>();
		
		while (indiceIzq < izquierda.size() && indiceDer < derecha.size()) {
			
			if (izquierda.get(indiceIzq).getScore() < derecha.get(indiceDer).getScore()) {
				arrayOrdenado.add(izquierda.get(indiceIzq));
				indiceIzq++;
			} 
			else {
				arrayOrdenado.add(derecha.get(indiceDer));
				indiceDer++;
			}
		}
		
		if (indiceIzq >= izquierda.size()) {
			while (indiceDer <= derecha.size()-1) {
				arrayOrdenado.add(derecha.get(indiceDer));
				indiceDer++;
			}
		} else if (indiceDer >= derecha.size()) {
			while (indiceIzq <= izquierda.size()-1) {
				arrayOrdenado.add(izquierda.get(indiceIzq));
				indiceIzq++;
			}
		}
		
		/*
		if (indiceIzq >= izquierda.size()) {
			arrayOrdenado.add(derecha.get(indiceDer));
			indiceDer++;
		}
		if (indiceDer >= derecha.size()) {
			arrayOrdenado.add(izquierda.get(indiceIzq));
			indiceIzq++;
		}
		*/
		
		if (arrayOrdenado.size() >= 10) {
			ArrayList<Player> auxIzq = new ArrayList<Player>();
			for (Player s: arrayOrdenado.subList(arrayOrdenado.size()-10, arrayOrdenado.size())) {
				auxIzq.add(s);
			}
			arrayOrdenado = auxIzq;
		}
		return arrayOrdenado;
	}
	
	public static ArrayList<Player> diezMejoresJugadores(int inicio, int fin, ArrayList<Player> auxiliar) {
		if (inicio >= fin) {
			//System.out.println(inicio + "-" + fin);
			ArrayList<Player> auxiliar2 = new ArrayList<Player>();
			auxiliar2.add(auxiliar.get(inicio));
			//System.out.println("Auxiliar 2" + auxiliar2);
			return auxiliar2;
		}
		int mitad = (inicio + fin) / 2;
		ArrayList<Player> izq = diezMejoresJugadores(inicio,mitad,auxiliar);
		//System.out.println(izq.get(izq.size()-1));
		ArrayList<Player> der = diezMejoresJugadores(mitad+1,fin,auxiliar);
		ArrayList<Player> arr = combinarArray(izq, der);
		return arr;
	}
	
	public static void cargarArchivo(String ruta) {
		estadisticas = new ArrayList<Player>();
		File f = new File(ruta);
		String[] items = null;
		try {
			Scanner scan = new Scanner(f);
			String linea = "";
			Player ultimoJugadorCargado = null;
			String ultimoNombreCargado = "";
			while(scan.hasNextLine()) {
				linea = scan.nextLine().trim();
				if (linea.isEmpty() || linea.startsWith("#")) continue;
				items = linea.split(";");
				if (items.length != 9) continue;
				double fg = comprobarValor(items[7]);
				double pts = comprobarValor(items[8]);
				if(!items[2].equals(ultimoNombreCargado)) {
					ultimoJugadorCargado = new Player(items[2], items[6], items[4], (int) (fg*pts/100));
					estadisticas.add(ultimoJugadorCargado);
					ultimoNombreCargado = items[2];
				} else {
					ultimoJugadorCargado.add(items[6], items[4], (int) (fg*pts/100));
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static double comprobarValor (String value) {
		if(value.isEmpty()) return 0;
		try {
			double d = Double.parseDouble(value.replace(",","."));
			return d;
		} catch ( NumberFormatException e ) {
			return 0;
		}
	}
}
