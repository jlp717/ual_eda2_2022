package practica_4;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ProyectoFinal { 
	
	private boolean esDirigido;
	private int nrNodos;
	private static ArrayList<String> resultadoArray = new ArrayList<>();
	private static HashMap<ArrayList<String>,Double> solucionFinal = new HashMap<>();
	public static int tamArray = 0;
	public static double costes;
	/*
	 * El grafo está creado con un HashMap puesto que no es necesario ordenar, por eso durante
	 * el código veremos HashMaps.
	 * También hemos de aclarar que está creado de esta forma pues que en su Key, se encuentra el 
	 * vértice inicial, mientras que en su Value, tenemos otro HashMap que a su vez contiene el 
	 * vértice final y el respectivo coste entre ambos. 
	 */
	private HashMap<String, HashMap<String, Double>> grafo;//Key=Vertice, Key2=, Vertice, Value=Peso
	private String origen;
	
	/**
	 * Aquí hemos creado el método main para mostrar las salidas y los tiempos de ejecución.
	 * @param args
	 */
	
	public static void main(String[] args) {
		ProyectoFinal tree = new ProyectoFinal("C:\\WORKSPACES\\EstructurasDatosAlgoritmosII\\Practica_1\\src\\org\\eda2\\practica04\\graphPrimKruskal.txt", "A");
		System.out.println("B A C K T R A C K I N G");
		System.out.println("*********");
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		tree.BackTracking();
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("La cantidad de soluciones es de: " + tamArray);
		tree.obtencionSolucionFinal();
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
		
		System.out.println("------------------------------------------");
		System.out.println("B R A N C H  &  B O U N D");
		System.out.println("*********");
		long startNano1 = System.nanoTime();
		long startMili1 = System.currentTimeMillis();
		ArrayList<String> pruebaBB = tree.TSPBaB(tree.getOrigen());
		System.out.println("Solución encontrada : " + pruebaBB + " Coste -> " + costes);
		long endNano1 = System.nanoTime();
		long endMili1 = System.currentTimeMillis();
		System.out.println("La cantidad de nodos es de: " + resultadoArray.size());
		System.out.println("Tiempo de ejecución: " + (endNano1-startNano1) + " nanosegundos. || " + (endMili1-startMili1) + " milisegundos.");
		
	}
	
	/**
	 * Creamos el constructor por defecto, el cual crea un grafo vacío.
	 */

	public ProyectoFinal() {
		this.esDirigido = true;
		this.grafo = new HashMap<String, HashMap<String, Double>>();
	}
	
	/**
	 * La idea al usar el this() es que haga una llamada al ProyectoFinal() de arriba.
	 * Este método constructor lo usamos como un cargarArchivo.
	 * @param archivo
	 * @param origen
	 */
	
	public ProyectoFinal(String archivo, String origen) {
		this(); 
		setOrigen(origen);
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
		setNrNodos(Integer.valueOf(linea));
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
	
	public void setNrNodos(int nrNodos) {
		this.nrNodos = nrNodos;
	}
	
	public int getNrNodos() {
		return this.nrNodos;
	}
	
	public String getOrigen() {
		return this.origen;
	}
	
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	public HashMap<ArrayList<String>,Double> getSolucionFinal(){
		return solucionFinal;
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
  	
  	/**
  	 * Los métodos restantes ya son los algoritmos correspondientes.
  	 * Hemos de decir que para poder comprobar los tiempos de una manera más cómoda 
  	 * para nosotros, decidimos colocarle una recolección de tiempos al inicio y al final de 
  	 * los métodos.
  	 * @return
  	 */
  	
  	public ArrayList<String> BackTracking() {
  		ArrayList<String> resultadoFinal = new ArrayList<String>(); 
  		HashMap<String, Boolean> visitados = new HashMap<String, Boolean>();
  		/**
  		 * Con nrNodos nos referimos a la cantidad de Vértices que se encuentran en el grafo.
  		 */
  	    String[] etapa = new String[this.nrNodos+1]; 
  	    int nivel = 1;
  	    arrancarEstructuras(visitados, etapa);
  	    BackTracking(etapa, nivel, visitados, resultadoFinal);
  	    return resultadoFinal;
  	}
  	
  	private void arrancarEstructuras(HashMap<String, Boolean> visitados, String[] etapa) {
		for (String vertice : grafo.keySet()) {
			visitados.put(vertice, false);
		}
		etapa[0] = origen;
		etapa[etapa.length-1] = origen;
		visitados.put(origen, true);
	}
  	
  	public double getCosteTotal (ArrayList<String> resultados) {
  		double costeTotal = 0;
		for (int i = 0; i < resultados.size() - 1; i++) {
			if (i < resultados.size()) {
				costeTotal += getPeso(resultados.get(i), resultados.get(i+1));
			}
		}
  		return costeTotal;
	}

	private void BackTracking(String[] etapa, int nivel, HashMap<String, Boolean> visitados, ArrayList<String> resultados) {
		/**
		 * Seleccionamos una nueva opción hasta que no hayan otras.
		 */
  	    for (Entry<String, Boolean> it : visitados.entrySet()) { 
  	        if(it.getValue()) {
  	        	//System.out.println(it.getValue() + " -> nodo ya visitado.");
  	        	/**
  	        	 * Continuamos únicamente si el nodo ya ha sido visitado.
  	        	 */
  	        	continue; 
  	        }
  	        if(seAcepta(etapa, nivel, it.getKey(), visitados)) { 
  	        	/**
  	        	 * Guardamos la opción.
  	        	 */
  	            etapa[nivel] = it.getKey();
  	            //System.out.println(it.getValue() + " nodo aceptado.");
  	            /**
  	             * Si la solución se encuentra completa, incluímos la solución.
  	             */
  	            if(nivel == visitados.size()-1) { 
  	                agregarEtapaASoluciones(etapa);
  	                mapaSolucionFinal(resultadoArray, getCosteTotal(resultadoArray));
  	                System.out.println("Solución encontrada : " + Arrays.toString(etapa) + " Coste -> " + getCosteTotal(resultadoArray));   
  	            /**
  	             * Si la solución no se encuentra completa, avanzamos en la etapa.
  	             */
  	            }else {
  	                visitados.put(it.getKey(), true);
  	                //System.out.println("Siguiente etapa");
  	                BackTracking(etapa, nivel+1, visitados, resultados);
  	                visitados.put(it.getKey(), false);
  	            }
  	        } else {
  	        	//System.out.println(it.getValue() + " -> nodo no aceptado.");
  	        }
  	    }
  	    /**
  	     * Marcamos como no explorado.
  	     */
  	    etapa[nivel] = null;
  	}
	/**
	 * Volcamos los datos en el HashMap de solucionFinal.
	 * @param resultadoArray
	 * @param costeTotal
	 */
  	private void mapaSolucionFinal(ArrayList<String> resultadoArray, double costeTotal) {
  		solucionFinal.put(new ArrayList<String>(resultadoArray), costeTotal);
	}
  	/**
  	 * Ordenamos la solucionFinal y sacamos cuál es el camino más óptimo.
  	 */
  	private void obtencionSolucionFinal() {
        ArrayList<Entry<ArrayList<String>, Double>> list = new ArrayList<>(solucionFinal.entrySet());
        list.sort(Entry.comparingByValue());
//        list.forEach(System.out::println);
        System.out.println("La solución más óptima es : " + list.get(0));
    }
  	/**
  	 * Agregamos una solución al ArrayList y le sumamos 1 al tamaño del Array.
  	 * @param etapa
  	 */
	private void agregarEtapaASoluciones(String[] etapa) {
  		resultadoArray.clear();
  		for (int i = 0; i < etapa.length; i++) {
  			resultadoArray.add(etapa[i]); 
  		}
  		tamArray++;
	}
	/**
	 * Decidimos si aceptar o no el nodo.
	 * @param etapa
	 * @param nivel
	 * @param posibleCandidato
	 * @param visitados
	 * @return
	 */
	private boolean seAcepta(String[] etapa, int nivel, String posibleCandidato, HashMap<String, Boolean> visitados) {
  	    boolean seAlcanza = contieneArista(etapa[nivel-1], posibleCandidato);
  	    boolean seAlcanzaUltimo = nivel == visitados.size()-1 ? contieneArista(posibleCandidato, etapa[0]) : true;
  	    return seAlcanza && seAlcanzaUltimo;
  	}
	
	// return the minimum value of the edges
	public double minEdgeValue() {
		double minimum = Double.MAX_VALUE;
	  		// Devuelve el menor valor de arista del grafo
	  		// Dos bucles 'for' anidados
		for (HashMap<String,Double> i : grafo.values()) {
			for (Double v: i.values()) {
				if (v<minimum) {
					minimum = v;
				}
			}
		}
		return minimum;
	 }

	  	// TSP - BaB - Best-First
	public ArrayList<String> TSPBaB(String source) {
		if (grafo.get(source) == null)
			return null;

		double minEdgeValue = minEdgeValue();

			// Constructor de clase PathNode
		PathNode firstNode = new PathNode(source);
		
		firstNode.setMinEdgeValue(minEdgeValue);

		PriorityQueue<PathNode> priorityQueue = new PriorityQueue<>();

		priorityQueue.add(firstNode);

		ArrayList<String> shortestCircuit = null;
		double bestCost = Double.MAX_VALUE;

		while(priorityQueue.size() > 0) {

				// Y (PathNode) = menorElemento de la cola de prioridad en funcion de 'estimatedCost'
			PathNode Y = priorityQueue.poll();

			if (Y.getEstimatedCost() >= bestCost)
	            break;
			else {
				String from = Y.lastVertexRes();
					// Si el numero de vertices visitados es n
					// y existe una arista que conecte 'from' con source
				if ((Y.getVisitedVertices() == getNrNodos()) &&
					(contieneArista(from, source))) {
						// Actualizar 'res' en Y añadiendo el vertice 'source'
						// Actualizar 'totalCost' en Y con Y.totalCost + weight(from, source)
						Y.addVertexRes(source);
						Y.setTotalCost(Y.getTotalCost()+getPeso(from,source));

					if (Y.getTotalCost() < bestCost) {
							// Actualizar 'bestCost' y 'shortestCircuit'
						bestCost = Y.getTotalCost();
						shortestCircuit = Y.getRes();

					}
				} else {
					String to;
						// Iterar para todos los vertices adyacentes a from,
						// a cada vertice lo denominamos 'to'
					for (String i : grafo.get(from).keySet()) {
						to = i;
						if (!Y.isVertexVisited(to)) { // hacer uso de la funcion 'isVertexVisited(vertex)' de PathNode


							PathNode X = new PathNode(Y); // Uso de constructor copia
									// Anadir 'to' a 'res' en X
									// Incrementar en 1 los vertices visitados en X
									// Actualizar 'totalCost' en X con Y.totalCost + weight(from, to)
							X.addVertexRes(to);
							X.setVisitedVertices(X.getVisitedVertices() + 1);
							X.setTotalCost(Y.getTotalCost()+getPeso(from,to));

									// Actualizar 'estimatedCost' en X con X.totalCost + ((nVertices - X.getVisitedVertices() + 1) * minEdgeValue)
							X.setEstimatedCost(X.getTotalCost()+((getNrNodos() - X.getVisitedVertices() + 1) * minEdgeValue));


							if (X.getEstimatedCost() < bestCost) {
								priorityQueue.add(X);
							}
						}
					}
				}
			}
		}
		costes = bestCost;
		resultadoArray = shortestCircuit;
		return shortestCircuit;
	}		
}