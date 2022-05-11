package practica_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mochila {
	
	private ArrayList<Objeto> objetos;
	private double pesoFinal;
	private double beneficioFinal;
	private int capacidad;
	private String archivo = System.getProperty("user.dir") + File.separator + "src" + File.separator + "practica_3" + File.separator;
	
	/*
	 * Creamos un método main para poder ver por consola la salida y comprobar que es lo que se desea.
	 */
	
	public static void main (String[] args) {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		Mochila prueba = new Mochila ("p03");
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("-------------------------------------------");
		System.out.println("\tArchivo Cargado");
		System.out.println("-------------------------------------------");
		imprimirDatos(prueba);
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		System.out.println("-------------------------------------------");
		System.out.println("\tKnapsack");
		System.out.println("-------------------------------------------");
		startNano = System.nanoTime();
		startMili = System.currentTimeMillis();
		ArrayList<Objeto> result = prueba.knapsack();
		endNano = System.nanoTime();
		endMili = System.currentTimeMillis();
		for (Objeto o : result) {
			System.out.println(o + " Con una cantidad de : " + o.getCantidad());
		}
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		System.out.println("-------------------------------------------");
		System.out.println("\tUnlimited Knapsack");
		System.out.println("-------------------------------------------");
		startNano = System.nanoTime();
		startMili = System.currentTimeMillis();
		result = prueba.knapsackSinLimites();
		endNano = System.nanoTime();
		endMili = System.currentTimeMillis();
		imprimirObjetosKnapsackSinLimites(result);
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		System.out.println("-------------------------------------------");
		System.out.println("\tGreedy Knapsack");
		System.out.println("-------------------------------------------");
		startNano = System.nanoTime();
		startMili = System.currentTimeMillis();
		result = prueba.greedyKnapsack();
		endNano = System.nanoTime();
		endMili = System.currentTimeMillis();
		for (Objeto o : result) {
			System.out.println(o + " Con una cantidad de : " + o.getCantidad());
		}
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
	}
	
	/*
	 * El constructor de la Mochila llama automáticamente al cargarArchivo.
	 */
	
	public Mochila(String nombreArchivo) {
		try {
			cargarArchivo(nombreArchivo);
		} catch (FileNotFoundException e) {
			System.out.println("Error al cargar el archivo. El sistema no puede encontrar el archivo.");
			System.exit(-1);;
		}
	}
	
	/*
	 * En un único método cargamos los tres archivos y sus respectivas excepciones en caso de error.
	 */
	
	private void cargarArchivo(String nombreArchivo) throws FileNotFoundException {
		File fichero = new File(archivo+nombreArchivo+"_c.txt");
		Scanner escaner = null;
		this.objetos = new ArrayList<Objeto>();
		try {
			escaner = new Scanner(fichero);
		} catch (Exception excepcion) {
			System.out.println("Error al cargar el archivo. El sistema no puede encontrar el archivo _c.txt.");
			System.exit(-1);
		}
		this.capacidad = Integer.parseInt(escaner.nextLine().trim());
		escaner.close();
		fichero = new File(archivo+nombreArchivo+"_w.txt");
		try {
			escaner = new Scanner(fichero);
		} catch (Exception ex) {
			System.out.println("Error al cargar el archivo. El sistema no puede encontrar el archivo _w.txt.");
			System.exit(-1);
		}
		ArrayList<Objeto> lista = new ArrayList<Objeto>();
		int i = 1;
		/*
		 * Dado que los pesos pueden ser Double o Integer, cubrimos esas posibilidades.
		 */
		while(escaner.hasNextLine()) {
			if(escaner.findInLine(".") != null) {
				lista.add(new Objeto("Objeto"+i,Double.parseDouble(escaner.nextLine().trim()),0));
				i++;
			} else {
				lista.add(new Objeto("Objeto"+i,Integer.parseInt(escaner.nextLine().trim()),0));
				i++;
			}
		}

		escaner.close();
		fichero = new File(archivo+nombreArchivo+"_p.txt");
		try {
			escaner = new Scanner(fichero);
		} catch (Exception ex) {
			System.out.println("Error al cargar el archivo. El sistema no puede encontrar el archivo _p.txt.");
			System.exit(-1);
		}
		i = 0;
		while(escaner.hasNextLine()) {
			lista.get(i).setBeneficio(Double.parseDouble(escaner.nextLine().trim()));
			i++;
		}
		escaner.close();
		objetos.addAll(lista);
	}
	
	/*
	 * Es un simple syso para mostrar el archivo cargado.
	 */
	
	private static void imprimirDatos(Mochila datos) {
		for (Objeto o : datos.objetos) {
			System.out.println(o);
		}
	}
	
	/*
	 * Es un método usado sólamente para el Knapsack sin Límites ya que obtenemos la cantidad
	 * de veces que se repite el elemento.
	 */
	
	public static ArrayList<Objeto> imprimirObjetosKnapsackSinLimites(ArrayList<Objeto> results) {
		ArrayList<Objeto> aux = new ArrayList<>();
		for (Objeto o : results) {
			if(results.contains(o) && !aux.contains(o)) {
				int i = 1;
				for (Objeto recorrido : results) {
					if(recorrido.equals(o)) {
						o.setCantidad(i++);
					}
				}
				System.out.println(o + " Con una cantidad de : " + o.getCantidad());
				aux.add(o);
			}
		}
		return aux;
	}
	
	/*
	 * Creamos el método knapsack por defecto.
	 */
	
	public ArrayList<Objeto> knapsack() {
		int mcd = gcd();
		/*
		 * Ordenamos ascendentemente por peso.
		 */
		this.objetos.sort(new Comparador()); 
		int n = this.objetos.size();
		double[][] B = new double[n + 1][capacidad/mcd + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= capacidad/mcd; j++) {
				if (this.objetos.get(i - 1).getPeso()/mcd <= j) {
					B[i][j] = Math.max(B[i - 1][j],
							this.objetos.get(i - 1).getBeneficio() + B[i - 1][(int) (j - this.objetos.get(i - 1).getPeso()/mcd)]);
				} else {
					B[i][j] = B[i - 1][j];
				}
			}
		}
		/*
		 * Hacemos una llamada al método sobrecargado "elementos" para recuperar los objetos. 
		 */
		return elementos(B, mcd); 
	}
	
	/*
	 * Se nos pide que se puede introducir objetos de peso Double e Integer, de modo que los siguientes métodos 
	 * sirven para aquello, uno para cada cosa.
	 */
	
	public ArrayList<Objeto> elementos(double[][] matriz, int mcd) {
		ArrayList<Objeto> resultado = new ArrayList<Objeto>();
		pesoFinal = 0;
		/*
		 * La siguiente variable se trata del valor máximo de la mochila.
		 */
		beneficioFinal = matriz[matriz.length-1][matriz[0].length-1];
		/*
		 * Se trata de la columna desde la que empezamos.
		 */
		int j = matriz[0].length - 1; 
		/*
		 * Conforme vaya iterando, se sube de fila.
		 */
		for (int i = matriz.length - 1; i > 0; i--) {
			if (matriz[i][j] != matriz[i - 1][j]) {
				resultado.add(this.objetos.get(i - 1));
				pesoFinal += this.objetos.get(i - 1).getPeso();
				/*
				 * Movemos la columna a la izquierda del peso del objeto
				 */
				j -= this.objetos.get(i - 1).getPeso()/mcd;
			}
		}
		return resultado;
	}
	
    private ArrayList<Objeto> elementos(double[] array, int mcd) {
		beneficioFinal = 0;
		pesoFinal = 0;
		ArrayList<Objeto> resultado = new ArrayList<Objeto>();
		double capRestante = this.capacidad/mcd;
		double pesoMinimo = this.objetos.get(0).getPeso();
		int size = this.objetos.size();
		while(capRestante >= pesoMinimo) { 
			double valorMaximo = 0;
			/*
			 * Definimos la posición del objeto ganador.
			 */
			int item = -1; 
			for (int i = size-1; i >= 0; i--) { 
				/*
				 * Únicamente tenemos en cuenta los objetos que quepan en el espacio restante.
				 */
				if(capRestante - this.objetos.get(i).getPeso()/mcd >= 0) { 
					double nuevoValor = array[(int) (capRestante - this.objetos.get(i).getPeso()/mcd)] + this.objetos.get(i).getBeneficio();
					if(nuevoValor > valorMaximo) {
						valorMaximo = nuevoValor;
						item = i;
					}
				}
			}
			/*
			 * Si ya no se encuentra ningún candidato, se acaba el bucle.
			 */
			if(item == -1) break; 
			beneficioFinal += this.objetos.get(item).getBeneficio();
			pesoFinal += this.objetos.get(item).getPeso();
			resultado.add(this.objetos.get(item));
			/*
			 * Decrementamos la capacidad de la mochila cada vez.
			 */
			capRestante -= this.objetos.get(item).getPeso(); 
		}
		return resultado;
	}
    
    /*
     * Creamos una versión de Knapsack donde cada elemento puede ser escogido de forma infinita.
     */
	
    public ArrayList<Objeto> knapsackSinLimites(){
    	int mcd = gcd();
		this.objetos.sort(new Comparador());
        int size = this.objetos.size();
		double[] array = new double[capacidad/mcd + 1]; // Array
		for (int i = 0; i <= capacidad/mcd; i++) {
			for (int j = 0; j < size; j++) {
				if(this.objetos.get(j).getPeso()/mcd  <= i) {
					array[i] = Math.max(array[i], array[(int) (i - this.objetos.get(j).getPeso()/mcd)]+this.objetos.get(j).getBeneficio());
				}
			}
		}
		return elementos(array, mcd);
	}
    
    /*
     * El siguiente método se encargará de fraccionar los elementos para actuar como un Knapsack 
     * maximizando el beneficio obtenido.
     */
    
	public ArrayList<Objeto> greedyKnapsack(){
		int mcd = gcd();
		this.objetos.sort(null); 
		beneficioFinal = 0;
		pesoFinal = 0;
		ArrayList<Objeto> resultado = new ArrayList<>();
		for (Objeto o : objetos) {
			/*
			 * Si el elemento cabe entero, se hace lo siguiente:
			 */
			if(pesoFinal + o.getPeso()/mcd <= capacidad/mcd) {
				o.setCantidad(1);
				resultado.add(o);
				beneficioFinal += o.getBeneficio();
				pesoFinal += o.getPeso();
				/*
				 * Si la capacidad máxima es alcanzada:
				 */
				if(pesoFinal == capacidad/mcd) break;
				/*
				 * Si no cabe entero:
				 */
			}else {
				o.setCantidad(((capacidad/mcd)-pesoFinal)/o.getPeso()/mcd);
				resultado.add(o);
				beneficioFinal += o.getBeneficio() * o.getCantidad();
				pesoFinal += (o.getPeso()) * o.getCantidad();
				/*
				 * Cuando no quepa más, se para.
				 */
				break; 
			}
		}
		return resultado;	
	}
	
	/*
	 * El método del gcd lo usamos para mejorar ligeramente el algoritmo, sumándole al orden de complejidad.
	 */
    
	private int gcd(int a, int b) {
		while (b > 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
	
	public int gcd() {
		int resultado = capacidad;
		for (int i = 0; i < objetos.size(); i++) {
			int peso = (int) Math.round (objetos.get(i).getPeso());
			resultado = gcd(resultado, peso);
			if (resultado == 1) {
				return 1;
			}
		}
		return resultado;
	}
}
