package practica_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Generador {
	
	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator + "practica_3" + File.separator;
	
	public static void main(String[] args) throws FileNotFoundException {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		generarObjetos(10,5,"p04",2);
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
	}
	
	/*
	 * Método usado para generar archivos dependiendo de la capacidad, las filas y de si se quiere que el peso sea Double o Integer.
	 */
	
	public static void generarObjetos(int capacidad, int nFilas, String nombreArchivo, int modo) throws FileNotFoundException {
		switch(modo) {
			case 1:
				PrintWriter pwCapacidad1 = new PrintWriter (new File (ruta + nombreArchivo + "_c.txt"));
				pwCapacidad1.println(capacidad);
				pwCapacidad1.close();
				PrintWriter pwPesos1 = new PrintWriter (new File (ruta + nombreArchivo + "_w.txt"));
				ArrayList<Integer> pesos1 = pesoAleatorioInt(capacidad, nFilas);
				for (int p : pesos1) {
					pwPesos1.println(" " + p);
				}
				pwPesos1.close();
				PrintWriter pwBeneficios1 = new PrintWriter (new File (ruta + nombreArchivo + "_p.txt"));
				ArrayList<Double> beneficios1 = beneficiosAleatorios(nFilas);
				for (double b : beneficios1) {
					pwBeneficios1.println(b);
				}
				pwBeneficios1.close();
				break;
			case 2:
				PrintWriter pwCapacidad2 = new PrintWriter (new File (ruta + nombreArchivo + "_c.txt"));
				pwCapacidad2.println(capacidad);
				pwCapacidad2.close();
				PrintWriter pwPesos2 = new PrintWriter (new File (ruta + nombreArchivo + "_w.txt"));
				ArrayList<Double> pesos2 = pesoAleatorioDouble(capacidad, nFilas);
				for (double p : pesos2) {
					pwPesos2.println(" " + p);
				}
				pwPesos2.close();
				PrintWriter pwBeneficios2 = new PrintWriter (new File (ruta + nombreArchivo + "_p.txt"));
				ArrayList<Double> beneficios2 = beneficiosAleatorios(nFilas);
				for (double b : beneficios2) {
					pwBeneficios2.println(b);
				}
				pwBeneficios2.close();
		}
	}
	
	/*
	 * Los métodos de pesoAleatorio se usan para crear pesos hasta llegar a la capacidad dada.
	 * Ponemos como condición que la cantidad máxima sea el doble de la capacidad para que haya más opciones para
	 * elegir.
	 */

	public static ArrayList<Integer> pesoAleatorioInt(int capacidad, int nFilas) {
		int cantidadMaxima = (int) (capacidad * 2);
	    Random r = new Random();
	    ArrayList<Integer> resultado = new ArrayList<>(nFilas);
	    /*
	     * Aqui obtenemos numeros aleatorios.
	     */
	    int sumaTotal = 0;
	    for (int i = 0; i < nFilas; i++) {
	        int siguiente = r.nextInt(cantidadMaxima) + 1;
	        resultado.add(siguiente);
	        sumaTotal += siguiente;
	    }
	    /*
	     * Se escala a la cantidad maxima deseada.
	     */
	    double escala = 1d * (cantidadMaxima) / sumaTotal;
	    sumaTotal = 0;
	    for (int i = 0; i < nFilas; i++) {
	        resultado.set(i, (int) (resultado.get(i) * escala));
	        sumaTotal += resultado.get(i);
	    }
	    /*
	     * Se corrigen errores de redondeo.
	     */
	    while(sumaTotal++ < (cantidadMaxima)) {
	        int i = r.nextInt(nFilas);
	        resultado.set(i, resultado.get(i) + 1);
	    }	    
	    return resultado;
	}
	
	public static ArrayList<Double> pesoAleatorioDouble(int capacidad, int nFilas) {
		int cantidadMaxima = (int) (capacidad * 2);
	    Random r = new Random();
	    ArrayList<Double> resultado = new ArrayList<>(nFilas);
	    /*
	     * Aqui obtenemos numeros aleatorios.
	     */
	    int sumaTotal = 0;
	    for (int i = 0; i < nFilas; i++) {
	        double siguiente = r.nextInt(cantidadMaxima) + 1;
	        resultado.add(siguiente);
	        sumaTotal += siguiente;
	    }
	    /*
	     * Se escala a la cantidad maxima deseada.
	     */
	    double escala = 1d * (cantidadMaxima) / sumaTotal;
	    sumaTotal = 0;
	    for (int i = 0; i < nFilas; i++) {
	        resultado.set(i, (resultado.get(i) * escala));
	        sumaTotal += resultado.get(i);
	    }
	    /*
	     * Se corrigen errores de redondeo.
	     */
	    while(sumaTotal++ < (cantidadMaxima)) {
	        int i = r.nextInt(nFilas);
	        resultado.set(i, resultado.get(i) + 1);
	    }	    
	    return resultado;
	}
	
	/*
	 * Finalmente, creamos un método para que obtenga beneficios aleatorios sin repetirse.
	 */
	
	public static ArrayList<Double> beneficiosAleatorios(int nFilas) {
	    ArrayList<Double> resultado = new ArrayList<>(nFilas);
	    while (resultado.size() < nFilas) {
	    	double beneficio = (Math.random()*(nFilas*10)+1);
	    	if (!resultado.contains(beneficio)) {
		    	resultado.add(beneficio);
	    	}
	    }
	    return resultado;
	}
}
