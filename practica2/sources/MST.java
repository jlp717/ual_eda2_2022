package practica_2_codigo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class MST { // MST -> Minimum Spanning Tree
	
	private final double INFINITO = Double.MAX_VALUE;
	private boolean esDirigido;
	/*
	 * El grafo está creado con un HashMap puesto que no es necesario ordenar, por eso durante
	 * el código veremos HashMaps y HashSets, quitando en varios casos donde sí que será necesario
	 * emplear TreeMaps y TreeSets.
	 * También hemos de aclarar que está creado de esta forma pues que en su Key, se encuentra el 
	 * vértice inicial, mientras que en su Value, tenemos otro HashMap que a su vez contiene el 
	 * vértice final y el respectivo peso entre ambos. 
	 */
	private HashMap<String, HashMap<String, Double>> grafo;//Key=Vertice, Key2=, Vertice, Value=Peso
	private String origen;
	
	/*
	 * Aquí hemos creado el método main para mostrar las salidas y los tiempos de ejecución.
	 */
	
	public static void main(String[] args) {
		//MST tree = new MST("C:\\WORKSPACES\\EDA_2022\\practica_2\\src\\graphPrimKruskal.txt", "A");
		MST tree = new MST("C:\\WORKSPACES\\EstructurasDatosAlgoritmosII\\Practica_1\\src\\org\\eda2\\practica02\\Prueba10Vertices.txt", "0");
		
		List<Arista> resultado = tree.prim();
		System.out.println(resultado);
		ContarCostes costes = new ContarCostes(resultado);
		System.out.println(costes);
		
		List<Arista> resultado1 = tree.primPQ();
		System.out.println(resultado1);
		ContarCostes costes1 = new ContarCostes(resultado1);
		System.out.println(costes1);
		
		List<Arista> resultado2 = tree.kruskal();
		System.out.println(resultado2);
		ContarCostes costes2 = new ContarCostes(resultado2);
		System.out.println(costes2);
	}
	
	/*
	 * Creamos el constructor por defecto, el cual crea un grafo vacío.
	 */

	public MST() {
		this.esDirigido = true;
		this.grafo = new HashMap<String, HashMap<String, Double>>();
	}
	
	/*
	 * La idea al usar el this() es que haga una llamada al MST() de arriba.
	 * Este método constructor lo usamos como un cargarArchivo.
	 */
	
	public MST(String archivo, String origen) {
		this(); 
		this.origen = origen;
		Scanner scan = null;
		String linea = "";
		String[] aux = null;

		try {
			scan = new Scanner(new File(archivo));
		} catch (Exception ex) {
			System.out.println("Error al cargar el archivo. El sistema no puede encontrar el archivo especificado.");
			System.exit(-1);
		}
		
		linea = scan.nextLine();
		setEsDirigido(linea.equals("0") ? false : true);
		
		linea = scan.nextLine();
		for (int i = 0; i < Integer.valueOf(linea); i++) {
			agregarVertice(scan.nextLine());
		}
		
		linea = scan.nextLine();
		for (int i = 0; i < Integer.valueOf(linea); i++) {
			aux = scan.nextLine().split(" ");
			agregarArista(aux[0], aux[1], Double.parseDouble(aux[2]));
		}
	}

	public boolean getEsDirigido() {
  		return this.esDirigido;
  	}
	
	public void setEsDirigido(boolean esDirigido) {
  		this.esDirigido = esDirigido;
  	}
	
	/*
	 * Ahora empezamos con los métodos orientados a la modificación del grafo.
	 */
	
	public boolean agregarVertice(String vertice) {
        if (this.grafo.containsKey(vertice)) return false;
        this.grafo.put(vertice, new HashMap<String, Double>());
        return true;
  	} 

  	public boolean agregarArista(String vertice1, String vertice2, double peso) {
  		agregarVertice(vertice1);
  		agregarVertice(vertice2);
  		/*
  		 * Agrega la arista, pues al primer vértice le añade el segundo vértice y su respectivo peso.
  		 */
  		this.grafo.get(vertice1).put(vertice2, peso); 
  		/*
  		 * Ocurre únicamente si no es dirigido. Pues si, por ejemplo, agregamos una arista de A - B, 
  		 * también se agrega una de B - A. Con otras palabras, en sentido contrario.
  		 */
  		if (!this.esDirigido) { 
        	this.grafo.get(vertice2).put(vertice1, peso);
       	}
    	return true;
  	}
  	
    private boolean contieneArista(String vertice1, String vertice2) {
		if (this.grafo.get(vertice1) != null && this.grafo.get(vertice1).get(vertice2) != null) return true;
		return false;
	}
  	
  	public Double getPeso (String vertice1, String vertice2) {
		return contieneArista(vertice1, vertice2) ? this.grafo.get(vertice1).get(vertice2) : null;
	}
  	
  	/*
  	 * Los métodos restantes ya son los algoritmos correspondientes.
  	 * Hemos de decir que para poder comprobar los tiempos de una manera más cómoda 
  	 * para nosotros, decidimos colocarle una recolección de tiempos al inicio y al final de 
  	 * los métodos.
  	 */

	public List<Arista> prim(){
    	long startNano = System.nanoTime();
    	long startMili = System.currentTimeMillis();
    	/*
    	 * Siempre que el origen sea nulo o que no se contenga la Key, muestra la siguiente excepción.
    	 */
		if(origen == null || !this.grafo.containsKey(origen)) throw new RuntimeException("No puede ser nulo.");
		
		/*
		 * Procedemos a inicializar las estructuras auxiliares.
		 */
		
		/*
		 * Donde la Key es el vértice en concreto y el Value es el peso.
		 */
		HashMap<String, Double> pesos = new HashMap<String, Double>();
		/*
		 * Donde la Key es el vértice inicial y el Value es el vértice final.
		 */
		HashMap<String, String> ramas = new HashMap<String, String>();
		HashSet<String> restantes = new HashSet<String>();
		List<Arista> resultadoFinal = new ArrayList<Arista>(); 
		String desde = null;
		/*
		 * El siguiente proceso sirve para incluir todos los vertices. No obstante, también se 
		 * podría hacer de la forma:
		 * 
		 * for(String vertice : this.grafo.keySet()) {
		 * 	if(!vertices.equals(origen)){
		 * 		restantes.add(vertice);
		 * 	}
		 * }
		 * 
		 * Sin embargo, de la forma en la que lo hemos hecho, es ligeramente más rápido.
		 */
		for (String vertice : this.grafo.keySet()) {
			restantes.add(vertice);
		}
		restantes.remove(origen);
		/*
		 * La idea de lo próximo es la de inicializar estructuras, donde se hallen todos los vértices
		 * exceptuando los adyacentes (los cuales denotamos como INFINITO).
		 * He ahí la razón de la condición del if-else siguiente.
		 */
		for (String vertice : restantes) {
			/*
			 * Es importante usar la clase Double para que de esta forma, sea posible contener
			 * valores nulos y de esa forma, poder recorrer el if-else adecuadamente.
			 */
			Double peso = getPeso(origen, vertice); 
			/*
			 * Son adyacentes al origen.
			 */
			if(peso != null) { 
				ramas.put(vertice, origen);
				pesos.put(vertice, peso);
			/*
			 * No son adyacentes al origen.
			 */
			}else { 
				ramas.put(vertice, null);
				pesos.put(vertice, INFINITO);
			}
			
		}
		ramas.put(origen, origen);
		pesos.put(origen, 0.0);
		/*
		 * La siguiente parte equivale al núcleo principal del algoritmo, mientras que el próximo bloque
		 * se trata de su función de selección. Esta reside en el hecho de que mientras aún queden vértices por valorar, 
		 * buscamos siempre el que tenga el menor peso, de ahí el if, dado que siempre que se encuentre un peso menor al
		 * actual mínimo, se actualiza.
		 */
		while(!restantes.isEmpty()) { 
			double min = INFINITO;
			desde = null;
			for (String v : restantes) {
				double peso = pesos.get(v);
				if(peso < min) { 
					min = peso;
					desde = v;
				}
			}
			/*
			 * Si no es conexo, paramos el bucle.
			 */
			if(desde == null) break;
			/*
			 * De los vértices restantes, eliminamos el vértice de menor peso.
			 */
			restantes.remove(desde);
			/*
			 * Obtenemos el vértice desde el que se llega.
			 */
			String aux = ramas.get(desde);
			/*
			 * Dicha arista se agrega al resultado final.
			 */
			resultadoFinal.add(new Arista (aux, desde, getPeso(aux, desde)));
			for (String hasta : restantes) {
				/*
				 * Como el peso puede ser nulo, dado que hemos acudido a la clase Double, tenemos que crear
				 * una condición para que lo verifique también. 
				 */
				Double peso = getPeso(desde, hasta);
				if(peso != null && peso < pesos.get(hasta)) {
					pesos.put(hasta, peso);
					ramas.put(hasta, desde);
				}
			}
		}

		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución para algoritmo Prim: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		return resultadoFinal;
		
	}

    public List<Arista> primPQ(){
    	long startNano = System.nanoTime();
    	long startMili = System.currentTimeMillis();
		String origen = this.origen;
		/*
    	 * Siempre que el origen sea nulo o que no se contenga la Key, muestra la siguiente excepción.
    	 */
		if(origen == null || !this.grafo.containsKey(origen)) throw new RuntimeException("No puede ser nulo.");
		/*
		 * Inicializamos las estructuras auxiliares.
		 */
		HashSet<String> restantes = new HashSet<String>();
		/*
		 * Es necesario crear una PQ de Aristas.
		 */
		PriorityQueue<Arista> colaDePrioridad = new PriorityQueue<Arista>();
		List<Arista> resultadoFinal = new ArrayList<Arista>(); 
		String desde = origen;
		String hasta;
		Double peso;
		Arista aux;
		/*
		 * Esta parte de aquí resulta ser la misma que la del algoritmo de Prim normal, de modo que su alternativa 
		 * también puede ser aceptable.
		 */
		for (String vertice : this.grafo.keySet()) {
			restantes.add(vertice);
		}
		restantes.remove(origen);
		/*
		 * La siguiente parte equivale al núcleo principal del algoritmo.
		 */
		while(!restantes.isEmpty()) {
			for (Entry<String, Double> iterador : this.grafo.get(desde).entrySet()) {
				hasta = iterador.getKey();
				peso = iterador.getValue();
				if(restantes.contains(hasta)) {
					aux = new Arista(desde, hasta, peso);
					colaDePrioridad.add(aux);
				}
			}
			/* 
			 * La función de selección se encuentra en el siguiente bloque, aunque técnicamente 
			 * sólo sería el poll de la cola de priodidad pues únicamente queremos obtener el 
			 * que menos peso tenga / el vértice más cercano.
			 */
			do {
				aux = colaDePrioridad.poll();
				desde = aux.getOrigen();
				hasta = aux.getDestino();
				peso = aux.getPeso();
			} while (!restantes.contains(hasta));
			
			restantes.remove(hasta);
			resultadoFinal.add(new Arista(aux.getOrigen(), aux.getDestino(), aux.getPeso()));
			desde = hasta;
		}
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución para algoritmo PrimPQ: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		return resultadoFinal;
	}

    public List<Arista> kruskal(){
    	long startNano = System.nanoTime();
    	long startMili = System.currentTimeMillis();
		String origen = this.origen;
		/*
    	 * Siempre que el origen sea nulo o que no se contenga la Key, muestra la siguiente excepción.
    	 */
		if(origen == null || !this.grafo.containsKey(origen)) throw new RuntimeException("No puede ser nulo.");
		/*
		 * Inicializamos las estructuras auxiliares.
		 */
		TreeMap<String,String> ramas = new TreeMap<String,String>();
		TreeMap<String,Double> restantes = new TreeMap<String,Double>();
		List<Arista> resultadoFinal = new ArrayList<Arista>();
		/*
		 * Debemos introducir todos los vértices y ponerle infinito como el peso.
		 */
		for (String vertice : grafo.keySet()) {
			restantes.put(vertice, INFINITO);
		}
		boolean esPrimero = true;
		while(!restantes.isEmpty()) {
			/*
			 * Buscamos la primera arista. También hemos de destacar que en la condición, 
			 * si se trata de la primera iteración que se produce, se empieza por el 
			 * origen.
			 */
			String keyMinima = restantes.firstKey();
			if(esPrimero) {
				keyMinima = origen;
				esPrimero = false;
			}
			/*
			 * Equivale a la función de selección pues se intenta buscar la arista más cercana / de menor peso.
			 */
			Double valorMinimo = INFINITO;
			for (Entry<String, Double> entrada : restantes.entrySet()) {
				if(entrada.getValue() < valorMinimo) {
					valorMinimo = entrada.getValue();
					keyMinima = entrada.getKey();
				}
			}
			/*
			 * Eliminamos la arista con valor mínimo del conjunto de los restantes.
			 */
			restantes.remove(keyMinima);
			/*
			 * Ahora debemos valorar para cada uno de sus vértices adyacentes.
			 */
			for (Entry<String, Double> iterador : grafo.get(keyMinima).entrySet()) {
				String hasta = iterador.getKey();
				Double dActual = getPeso(ramas.get(hasta), hasta);
				dActual = dActual == null ? INFINITO : dActual;
				/*
				 * Si de alguna forma se mejora el resultado actual, se actualiza y se agrega a la
				 * solución.
				 */
				if(restantes.containsKey(hasta) && iterador.getValue() < INFINITO && iterador.getValue() < dActual) {
					restantes.put(hasta, iterador.getValue());
					ramas.put(hasta, keyMinima);
					
				}
			}
		}
		/*
		 * En el siguiente bloque trataremos de crear el conjunto de resultados a partir de las aristas.
		 */
		for (Entry<String, String> iterador : ramas.entrySet()) {
			String hasta = iterador.getKey();
			String desde = iterador.getValue();
			resultadoFinal.add(new Arista (hasta, desde, getPeso(hasta, desde)));
		}
		long endNano = System.nanoTime();
    	long endMili = System.currentTimeMillis();
    	System.out.println("Tiempo de ejecución para algoritmo Kruskal: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		return resultadoFinal;
	}
}

