package practica_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Generador {

	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator + "practica_4" + File.separator;
	private static ArrayList<String> temp = new ArrayList<String>();
	public static void main(String[] args) throws FileNotFoundException {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		grafoNoDirigido(15,"Prueba15Vertices.txt");
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
	}
	
	private static void grafoNoDirigido(int nVertices, String nombreArchivo) throws FileNotFoundException {
		/*
		 * Creamos las instancias necesarias.
		 */
		Random random = new Random();
		PrintWriter pw = new PrintWriter (new File (ruta + nombreArchivo));
		/*
		 * Imprimimos el 0 (significando que el grafo no es dirigido) junto al número de vértices
		 * para seguir la sintaxis mostrada.
		 */
		pw.println("0");
		pw.println(nVertices);
		/*
		 * Inicializamos las estructuras necesarias.
		 */
		ArrayList<AristaGenerador> aux = new ArrayList<AristaGenerador>();
		/*
		 * Únicamente se trata de obtener un número aleatorio entre 2 y 3. De forma resumida,
		 * hemos dicho que dado un número de vértices, debe hacer mínimo el doble y máximo el triple
		 * de aristas.
		 */
		int nAristas = nVertices*(random.nextInt(2)+2);
		ArrayList<AristaGenerador> resultadoFinal = new ArrayList<AristaGenerador>(nAristas);
		/*
		 * Imprimimos los vértices de forma consecutiva.
		 */
		for (int i = 0; i < nVertices; i++) {
			pw.println(i);
		}
		/*
		 * En el siguiente bloque agregamos al arrayList final aquellos números que son consecutivos, mientas que 
		 * los que no lo son, se añaden al auxiliar. Junto a todo esto, también se les asigna un peso aleatorio.
		 */
		for (int i = 0; i < nVertices; i++) {
			for (int j = i+1; j < nVertices; j++) {
				int pesoAleatorio = (int) (Math.random()*100+1);
				if(i+1 == j) {//Si son numeros consecutivos, se agrega
					resultadoFinal.add(new AristaGenerador (i, j, pesoAleatorio));
					temp.add(i + " " + j);
				}else {//Si no, pasan a la lista auxiliar de aristas restantes
					aux.add(new AristaGenerador(i, j, pesoAleatorio));
				}
			}
		}
		
		pw.println(nAristas);
		/*
		 * El bloque que hay a continuación, crea las aristas restantes. Pero para ello hay que comprobar que no estén ya
		 * en la solución final.
		 */
		int elementosAristas = 0;
		while(elementosAristas < resultadoFinal.size() && resultadoFinal.size() < nAristas) {
			int indiceAleatorio = random.nextInt(aux.size());
			while (contieneArista(resultadoFinal, aux.get(indiceAleatorio))) {
				indiceAleatorio = random.nextInt(aux.size());
			} 
			
			resultadoFinal.add(aux.get(indiceAleatorio));
			elementosAristas++;
		}
		
		for (AristaGenerador a : resultadoFinal) {
			pw.println(a);
		}
		
		pw.close();
	}
	
	/*
	 * Este método es creado para comprobar que no se contenga la arista en una lista correspondiente.
	 */
	public static boolean contieneArista(ArrayList<AristaGenerador> c, AristaGenerador a ) {
		String str1 = a.getOrigen() + " " + a.getDestino();
		String str2 = a.getDestino() + " " + a.getOrigen();
		if(temp.contains(str1) || temp.contains(str2)) {
			temp.add(str1);
			return true;
		} else {
			temp.add(str1);
		    return false;
		}
	}	
}