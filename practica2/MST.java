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
	private HashMap<String, HashMap<String, Double>> grafo;//Key=Vertice, Key2=, Vertice, Value=Peso
	private String origen;
	
	public static void main(String[] args) {
		MST tree = new MST("C:\\WORKSPACES\\EDA_2022\\practica_2\\src\\graphPrimKruskal.txt", "A");
//		MST tree = new MST("C:\\WORKSPACES\\EstructurasDatosAlgoritmosII\\Practica_1\\src\\org\\eda2\\practica02\\Prueba01.txt", "0");
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

	public MST() {
		this.esDirigido = true;
		this.grafo = new HashMap<String, HashMap<String, Double>>();
	}
	
	public MST(String archivo, String origen) {
		this(); // Hace una llamada al MST de arriba.
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
	
	public HashMap<String, HashMap<String, Double>> getGrafo() {
		return grafo;
	}
	
	public String getOrigen() {
		return origen;
	}

    public void setOrigen(String origen) {
		this.origen = origen;
	}

	public boolean getEsDirigido() {
  		return this.esDirigido;
  	}
	
	public void setEsDirigido(boolean esDirigido) {
  		this.esDirigido = esDirigido;
  	}
	
	public boolean agregarVertice(String vertice) {
        if (this.grafo.containsKey(vertice)) return false;
        this.grafo.put(vertice, new HashMap<String, Double>());
        return true;
  	} 

  	public boolean agregarArista(String vertice1, String vertice2, double peso) {
  		agregarVertice(vertice1);
  		agregarVertice(vertice2);
  		this.grafo.get(vertice1).put(vertice2, peso); // Agrega la arista, pues al primer vértice le añade el segundo vértice y su respectivo peso.
  		if (!this.esDirigido) { // Ocurre únicamente si no es dirigido. Pues si, por ejemplo, agregamos una arista de A - B, también se agrega una de B - A. Con otras palabras, en sentido contrario.
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


	public List<Arista> prim(){
		if(origen == null || !this.grafo.containsKey(origen)) throw new RuntimeException("No puede ser nulo.");
		
		//Inicializacion de estructuras auxiliares
		HashMap<String, Double> pesos = new HashMap<String, Double>();//Key=Vertice, Value=Peso
		HashMap<String, String> ramas = new HashMap<String, String>();//Key=Vertice, Value=Vertice
		HashSet<String> restantes = new HashSet<String>();//Vertices
		List<Arista> resultadoFinal = new ArrayList<Arista>(); 
		String desde = null;
		
		//Incluir todos los vertices
		for (String vertice : this.grafo.keySet()) {
			restantes.add(vertice);
		}
		restantes.remove(origen);
		
		//Inicializar estructuras (todos los vertices excepto adyacentes valorados a infinito)
		for (String vertice : restantes) {
			Double peso = getPeso(origen, vertice); //Acudimos a la clase Double para poder albergar elementos nulos.
			if(peso != null) { //adyacentes al origen
				ramas.put(vertice, origen);
				pesos.put(vertice, peso);
			}else { //no adyacentes al origen
				ramas.put(vertice, null);
				pesos.put(vertice, INFINITO);
			}
			
		}
		ramas.put(origen, origen);
		pesos.put(origen, 0.0);
		
		
		//Nucleo principal del algoritmo
		while(!restantes.isEmpty()) { //Mientras queden vertices por valorar, buscamos el que tenga el menor peso.
			//Busqueda del vertice de menor peso
			double min = INFINITO;
			desde = null;
			for (String v : restantes) {//Para cada vertice de los restantes...
				double weight = pesos.get(v);
				if(weight < min) { //Si tiene menor peso que el valor MIN se actualiza
					min = weight;
					desde = v;
				}
			}
			
			if(desde == null) break; //Se para porque no es conexo
			restantes.remove(desde); //Se elimina el vertice de menos peso de los restantes
			String aux = ramas.get(desde); //Se obtiene el vertice desde el que se llega
			resultadoFinal.add(new Arista (aux, desde, getPeso(aux, desde))); //Se agrega la arista a la solucion
			//Para el resto de vertices restantes
			for (String hasta : restantes) {
				Double peso = getPeso(desde, hasta); //CUIDADO, PUEDE SER NULO
				if(peso != null && peso < pesos.get(hasta)) {
					pesos.put(hasta, peso);
					ramas.put(hasta, desde);
				}
			}
		}

		return resultadoFinal;
		
	}

    public List<Arista> primPQ(){
		String origen = this.origen;
		if(origen == null || !this.grafo.containsKey(origen)) throw new RuntimeException("No puede ser nulo.");
		//Inicializacion de estructuras auxiliares
		HashSet<String> restantes = new HashSet<String>();//Vertices
		PriorityQueue<Arista> colaDePrioridad = new PriorityQueue<Arista>(); //Cola de prioridad de Aristas
		List<Arista> resultadoFinal = new ArrayList<Arista>(); 
		String desde = origen;
		String hasta;
		Double peso;
		Arista aux;

		//Incluir todos los vertices
		for (String vertice : this.grafo.keySet()) {
			restantes.add(vertice);
		}
		restantes.remove(origen);
		
		//Nucleo principal del algoritmo
		while(!restantes.isEmpty()) {
			for (Entry<String, Double> iterador : this.grafo.get(desde).entrySet()) {
				hasta = iterador.getKey();
				peso = iterador.getValue();
				if(restantes.contains(hasta)) {
					aux = new Arista(desde, hasta, peso);
					colaDePrioridad.add(aux);
				}
			}
			
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
		return resultadoFinal;
	}

    public List<Arista> kruskal(){
		String origen = this.origen;
		if(origen == null || !this.grafo.containsKey(origen)) throw new RuntimeException("No puede ser nulo.");
		//Inicializacion de estructuras auxiliares
		TreeMap<String,String> ramas = new TreeMap<String,String>();//Key=Vertice, Value=Vertice
		TreeMap<String,Double> restantes = new TreeMap<String,Double>();//Key=Vertice, Value=Peso
		List<Arista> resultadoFinal = new ArrayList<Arista>();
		
		//Incluir todos los vertices y el coste infinito
		for (String vertice : grafo.keySet()) {
			restantes.put(vertice, INFINITO);
		}
		boolean esPrimero = true;
		while(!restantes.isEmpty()) {
			//Busqueda de la menor arista
			String keyMinima = restantes.firstKey();
			if(esPrimero) {//Si es la primera iteración, el punto de partida es origen
				keyMinima = origen;
				esPrimero = false;
			}
			Double valorMinimo = INFINITO;
			for (Entry<String, Double> entrada : restantes.entrySet()) {
				if(entrada.getValue() < valorMinimo) {
					valorMinimo = entrada.getValue();
					keyMinima = entrada.getKey();
				}
			}
			restantes.remove(keyMinima);//Se elimina la arista del conjunto
			
			//Para cada uno de sus vertices adyacentes...
			for (Entry<String, Double> iterador : grafo.get(keyMinima).entrySet()) {
				String hasta = iterador.getKey();
				Double dActual = getPeso(ramas.get(hasta), hasta);
				dActual = dActual == null ? INFINITO : dActual;
				//...se actualiza y agrega a la solucion si mejora el resultado actual
				if(restantes.containsKey(hasta) && iterador.getValue() < INFINITO && iterador.getValue() < dActual) {
					restantes.put(hasta, iterador.getValue());
					ramas.put(hasta, keyMinima);
					
				}
			}
		}
		
		//A partir de las aristas, se crea el conjunto de resultados
		for (Entry<String, String> iterador : ramas.entrySet()) {
			String to = iterador.getKey();
			String from = iterador.getValue();
			resultadoFinal.add(new Arista (to, from, getPeso(to, from)));
		}
		return resultadoFinal;
	}

}